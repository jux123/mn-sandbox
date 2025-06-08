plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.25"
    id("com.google.devtools.ksp") version "1.9.25-1.0.20"
    id("io.micronaut.application") version "4.5.3"
    id("io.micronaut.aot") version "4.5.3"
    id("com.gradleup.shadow") version "8.3.6"
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


