plugins {
    java
    kotlin("jvm")
}

group = "com.youfun"
version = "0.0.1-SNAPSHOT"


repositories {
    mavenCentral()
}

dependencies {
    val jacsonversion="2.13.4"
    implementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.10.0"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.+")
    implementation ("com.fasterxml.jackson.core:jackson-core:$jacsonversion")
    implementation ("com.fasterxml.jackson.core:jackson-databind:$jacsonversion")
    implementation ("com.fasterxml.jackson.core:jackson-annotations:$jacsonversion")

    // define any required OkHttp artifacts without version
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}