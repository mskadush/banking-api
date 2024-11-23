plugins {
  kotlin("jvm") version "1.9.25"
  kotlin("plugin.spring") version "1.9.25"
  id("org.springframework.boot") version "3.4.0"
  id("io.spring.dependency-management") version "1.1.6"
}

group = "com.banking.api"
version = "0.0.1-SNAPSHOT"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(17)
  }
}

repositories {
  mavenCentral()
}

dependencies {
  implementation(libs.spring.boot.actuator)
  implementation(libs.spring.boot.data.mongodb)
  implementation(libs.spring.boot.web)
  implementation(libs.jackson.module.kotlin)
  implementation(libs.micrometer.tracing)
  implementation(libs.kotlin.reflect)
  implementation(libs.logging)
  implementation(project(":banking-logic"))
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.9.0")
  implementation("com.google.code.gson:gson:2.11.0")
  implementation("software.amazon.awssdk:sns:2.29.19")
  runtimeOnly("com.microsoft.sqlserver:mssql-jdbc")
  runtimeOnly("io.micrometer:micrometer-registry-otlp")
  implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
  compilerOptions {
    freeCompilerArgs.addAll("-Xjsr305=strict")
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}
