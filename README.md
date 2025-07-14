## Run app
### Gradle run
`./gradlew run`
### Build jar
`./gradlew assemble`

`java -jar build/libs/mn-sandbox-0.1-all.jar`

### Run in container:
`./gradlew clean dockerBuild`

`docker run -p 8080:8080 mn-sandbox:latest`

URL is http://localhost:8080/hello

## Gradle versions plugin -- bugs
Plugin:
`id("com.github.ben-manes.versions") version "0.52.0"`

Command:
`./gradlew dependencyUpdates -Drevision=release`

## OpenAPI output:
build/generated/ksp/main/resources/META-INF/swagger/sandbox-0.1.yml
### UI:
localhost:8080/swagger-ui


## OpenAPI diff:
Plugin: `com.x3t.gradle.plugins.openapi.openapi_diff` 

Checks the diff, is made part of build
```
./gradle openapi_diff
```
Updates the OpenAPI stored files
```
./gradlew openApiUpdate
```

Alternative custom diff:
```
./gradlew openApiDiff
```



## AWS SDK integration

### AWS region is set up in props
```
aws:
  region: eu-north-1
```

### Set AWS access keys to env before starting app
```declarative
export AWS_ACCESS_KEY_ID="ASIA4LQCO..."
export AWS_SECRET_ACCESS_KEY="0/Ao/PqR..."
export AWS_SESSION_TOKEN="IQoJb3..."
```

## Prometheus metrics
http://localhost:8080/metrics
http://localhost:8080/prometheus

Example usage
`curl http://localhost:8080/prometheus | grep jvm_memory_used`

## Visualize Prometheus metrics with Metricat
https://metricat.dev/


## Monitoring with VisualVM
```declarative
sudo apt install default-jre-headless visualvm
```
run visualvm and open the java process and see the collected metrics


## Micronaut 4.8.2 Documentation

- [User Guide](https://docs.micronaut.io/4.8.2/guide/index.html)
- [API Reference](https://docs.micronaut.io/4.8.2/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/4.8.2/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

- [Micronaut Gradle Plugin documentation](https://micronaut-projects.github.io/micronaut-gradle-plugin/latest/)
- [GraalVM Gradle Plugin documentation](https://graalvm.github.io/native-build-tools/latest/gradle-plugin.html)
- [Shadow Gradle Plugin](https://gradleup.com/shadow/)
## Feature ksp documentation

- [Micronaut Kotlin Symbol Processing (KSP) documentation](https://docs.micronaut.io/latest/guide/#kotlin)

- [https://kotlinlang.org/docs/ksp-overview.html](https://kotlinlang.org/docs/ksp-overview.html)


## Feature kotest documentation

- [Micronaut Test Kotest5 documentation](https://micronaut-projects.github.io/micronaut-test/latest/guide/#kotest5)

- [https://kotest.io/](https://kotest.io/)


## Feature micronaut-aot documentation

- [Micronaut AOT documentation](https://micronaut-projects.github.io/micronaut-aot/latest/guide/)


## Feature serialization-jackson documentation

- [Micronaut Serialization Jackson Core documentation](https://micronaut-projects.github.io/micronaut-serialization/latest/guide/)


## Problems upgradeing to Micronaut 4.9.0

`@GET("/test")` annotation does not get mapped to this sub-path in routing.

Debug in `/micronaut-router-4.9.5-sources.jar!/io/micronaut/web/router/DefaultRouter.java:708` to see the routes.