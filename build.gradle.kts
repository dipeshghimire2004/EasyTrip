plugins {
    java
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "org.easytrip"
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
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    runtimeOnly("org.postgresql:postgresql")

    //lombok
    implementation("org.projectlombok:lombok:1.18.34") // Check for latest version if needed
    annotationProcessor("org.projectlombok:lombok:1.18.34")

    implementation("org.springframework.boot:spring-boot-devtools:3.4.4")

    //swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.4")

    //email notification
    implementation("org.springframework.boot:spring-boot-starter-mail:3.4.4")

    //JWR for authentication
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // Logging with SLF4J and Logback
    implementation("org.springframework.boot:spring-boot-starter-logging:3.4.4")

    //security and validation
    implementation("org.springframework.boot:spring-boot-starter-security:3.4.4")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.4.4")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// Configure the bootJar task to name the JAR "app.jar"
tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar"){
    archiveFileName.set("app.jar")
}