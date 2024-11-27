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
    google()
    jcenter()
}

dependencies {
    // Основные зависимости Compose и другие
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
    implementation("org.jetbrains.compose.material:material-icons-extended-desktop:1.1.0")
    implementation("org.xerial:sqlite-jdbc:3.36.0.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
    implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")
    implementation("com.arkivanov.decompose:decompose:2.0.0-alpha-02")
    implementation("com.arkivanov.decompose:extensions-compose-jetbrains:1.0.0")
    implementation("org.kodein.di:kodein-di-framework-compose:7.19.0")
    implementation("io.github.rybalkinsd:kohttp:0.12.0")
    implementation("androidx.compose.ui:ui-test-junit4-desktop:1.7.0")

    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.0")

    // Замените на стабильную версию
    testImplementation("org.mockito:mockito-core:5.14.2")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")

    testImplementation("io.mockk:mockk:1.13.12")  // Для MockK, если он используется
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
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
