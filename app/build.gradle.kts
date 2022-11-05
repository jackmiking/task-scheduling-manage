plugins {
    java
    kotlin("jvm")
    id("org.springframework.boot")
}

group = "com.youfun"
version = "0.0.1-SNAPSHOT"



dependencies {
    implementation(kotlin("stdlib"))
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    implementation(project(":core"))
    //implementation(project(":common"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    runtimeOnly("com.h2database:h2")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}