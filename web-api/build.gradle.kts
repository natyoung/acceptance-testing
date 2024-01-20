plugins {
    java
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "org.scrumfall"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_20
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.1")
    implementation("commons-codec:commons-codec:1.15")
    implementation("com.google.code.gson:gson:2.10.1")
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.0")
    testImplementation("au.com.dius.pact.consumer:junit5:4.6.4")
    testImplementation("au.com.dius.pact.provider:junit5spring:4.5.3")
    testImplementation("com.github.javafaker:javafaker:1.0.2") { exclude("org.yaml") }
    testImplementation("org.yaml:snakeyaml:2.0")
}

tasks.withType<Test> {
    useJUnitPlatform()

    systemProperty("pact.rootDir", System.getenv("PACT_FOLDER"))
    systemProperty("pactfolder.path", System.getenv("PACT_FOLDER"))
    systemProperty("pact_do_not_track", true)
    systemProperty("pact.writer.overwrite", true)
}
