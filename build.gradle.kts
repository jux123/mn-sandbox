import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

plugins {
    id("org.jetbrains.kotlin.jvm") version "2.1.20"
    id("org.jetbrains.kotlin.plugin.allopen") version "2.1.20"
    id("com.google.devtools.ksp") version "2.1.20-2.0.1"
    id("io.micronaut.application") version "4.5.4"
    id("io.micronaut.aot") version "4.5.4"
    id("com.gradleup.shadow") version "8.3.6"
//    id("com.x3t.gradle.plugins.openapi.openapi_diff") version "1.1.0"
}

version = "0.1"
group = "com.sandbox"

val kotlinVersion=project.properties.get("kotlinVersion")
repositories {
    mavenCentral()
}

dependencies {
    ksp("io.micronaut:micronaut-http-validation")
    ksp("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.10.1")
//    implementation("org.reactivestreams:reactive-streams:1.0.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-rx3:1.10.1")

    //AWS SDK V2 dependencies
    implementation("io.micronaut.aws:micronaut-aws-sdk-v2")
    implementation("software.amazon.awssdk:s3")
    // implementation "software.amazon.awssdk:s3", {
    //     exclude group: "software.amazon.awssdk", module: "apache-client"
    //     exclude group: "software.amazon.awssdk", module: "netty-nio-client"
    // }

    // OpenApi + swagger
    ksp("io.micronaut.openapi:micronaut-openapi")
    compileOnly("io.micronaut.openapi:micronaut-openapi-annotations")

    compileOnly("io.micronaut:micronaut-http-client")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    runtimeOnly("org.yaml:snakeyaml")
    testImplementation("io.micronaut:micronaut-http-client")
}


application {
    mainClass = "com.sandbox.ApplicationKt"
}
java {
    sourceCompatibility = JavaVersion.toVersion("21")
}


graalvmNative.toolchainDetection = false

micronaut {
    runtime("netty")
    testRuntime("kotest5")
    processing {
        incremental(true)
        annotations("com.sandbox.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
    }
}


tasks.named<io.micronaut.gradle.docker.NativeImageDockerfile>("dockerfileNative") {
    jdkVersion = "21"
}

tasks.register("openApiUpdate") {
    doLast {
        val newFilesPath = "build/generated/ksp/main/resources/META-INF/swagger/"
        val existingFilesPath = "dist/swagger/"

        val newFiles = fileTree(newFilesPath) {
            include("*.yml")
        }
        // val existingFiles = fileTree(existingFilesPath) {
        //     include("*.yml")
        // }
        newFiles.forEach {
            println("Copying OpenAPI file: ${it.name}")
            Files.copy(
                Paths.get(newFilesPath, it.name),
                Paths.get(existingFilesPath, it.name),
                StandardCopyOption.REPLACE_EXISTING
            )
        }
        
    }
}

tasks.register("openApiDiff") {
    doLast {
        val newFilesPath = "build/generated/ksp/main/resources/META-INF/swagger/"
        val existingFilesPath = "dist/swagger/"
        val filePath1 = Paths.get(newFilesPath, "sandbox-0.1.yml")
        val filePath2 = Paths.get(existingFilesPath, "sandbox-0.1.yml")
        val file1 = Files.readAllLines(filePath1)
        val file2 = Files.readAllLines(filePath2)

        file1.forEachIndexed { index, line ->
            if (!file2.contains(line)) {
                println("Difference found in file: ${filePath1.fileName}")
                println("Line ${index + 1}: $line")
                throw GradleException("OpenAPI files differ")
            }
        }
    }
}

//openapi_diff {
//    originalFile = "dist/swagger/sandbox-0.1.yml"
//    newFile = "build/generated/ksp/main/resources/META-INF/swagger/sandbox-0.1.yml"
//    failOnChange = true
//    textReport = true
//}
//
//tasks.named("build") {
//    dependsOn("openapi_diff")
//}