import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization") version "1.5.31"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(compose.desktop.currentOs)

    implementation(compose.material3)
        //extended icon pack
    implementation("org.jetbrains.compose.material:material-icons-extended-desktop:1.1.0")
    // sqlite
    implementation("org.xerial:sqlite-jdbc:3.36.0.3")

        //kotlinx serializer probable remove later
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")

    //kotlin multiplatform logger
    implementation ("io.github.oshai:kotlin-logging-jvm:5.1.0")

    // decompose implementations
    implementation("com.arkivanov.decompose:decompose:2.0.0-alpha-02")
    implementation("com.arkivanov.decompose:extensions-compose-jetbrains:1.0.0")

        //kodein implementation (light-weighr di)
    implementation("org.kodein.di:kodein-di-framework-compose:7.19.0")

    //http library
    implementation("io.github.rybalkinsd:kohttp:0.12.0")
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "mpi"
            packageVersion = "1.0.0"
        }
    }
}
