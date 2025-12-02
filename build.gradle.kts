
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
