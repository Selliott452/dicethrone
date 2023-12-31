import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    id("org.springframework.boot") version "3.1.3"
    id("io.spring.dependency-management") version "1.1.3"
    id("com.google.cloud.tools.jib") version "3.3.2"
    id("org.flywaydb.flyway") version "9.22.0"

    id("org.springdoc.openapi-gradle-plugin") version "1.7.0"
}

group = "com.elliott"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql")
    implementation("com.google.cloud.sql:postgres-socket-factory:1.13.1")
    implementation("org.flywaydb:flyway-core:9.22.0")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

rootProject.afterEvaluate {
    project.tasks.named("forkedSpringBootRun").configure {
        doNotTrackState("See https://github.com/springdoc/springdoc-openapi-gradle-plugin/issues/102")
    }
}

jib {
    from {
        image = "eclipse-temurin"
    }
    to {
        image = "us-east1-docker.pkg.dev/dice-throne-398523/dice-throne/back-end"
    }
    container {
        mainClass = "com.elliott.dicethrone.DicethroneApplicationKt"
    }
}
