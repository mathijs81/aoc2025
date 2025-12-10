
plugins {
    kotlin("jvm") version "2.2.21"
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21)
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }
}

dependencies {
    implementation("com.google.ortools:ortools-java:9.14.6206")
}
