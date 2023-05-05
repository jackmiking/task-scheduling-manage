/*
 * This file was generated by the Gradle 'init' task.
 *
 * This is a general purpose Gradle build.
 * Learn more about Gradle by exploring our samples at https://docs.gradle.org/7.5.1/samples
 */
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion = "1.8.10"
val mapstructVersion="1.5.5.Final"

plugins {
    val kotlinVersion = "1.8.10"
    id("org.springframework.boot") version "3.0.6" apply false
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("plugin.lombok") version kotlinVersion
    id("io.freefair.lombok") version "8.0.1"
}

group = "com.youfun"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17



dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
dependencyManagement {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}



subprojects {
    apply {
        plugin("java")
        plugin("io.spring.dependency-management")
        plugin("io.freefair.lombok")
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib")
        implementation(kotlin("stdlib"))
        implementation("org.mapstruct:mapstruct:${mapstructVersion}")
        annotationProcessor("org.mapstruct:mapstruct-processor:${mapstructVersion}")
        testAnnotationProcessor("org.mapstruct:mapstruct-processor:${mapstructVersion}")
    }
    dependencyManagement {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:2022.0.2")
        }
    }
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }
    tasks.named<Test>("test") {
        useJUnitPlatform()
        filter {
            includeTestsMatching("*Test.*")
            isFailOnNoMatchingTests = false
        }

    }


}
