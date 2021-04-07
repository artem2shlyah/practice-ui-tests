plugins {
    kotlin("jvm") version "1.4.21"
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.junit.jupiter:junit-jupiter:5.4.2")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.2.5")
    testImplementation("org.assertj:assertj-core:3.19.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    implementation("org.seleniumhq.selenium:selenium-java:3.14.0")
    implementation("org.seleniumhq.selenium:selenium-chrome-driver:3.14.0")
}

tasks {
    test {
        useJUnitPlatform()
    }
}
