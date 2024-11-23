import org.jetbrains.kotlin.com.intellij.openapi.vfs.StandardFileSystems.jar
import java.nio.file.Path

plugins {
    kotlin("jvm") version "1.9.22"
    application
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

dependencies {
    kotlin("stdlib")
    implementation("com.google.code.gson:gson:2.11.0")
}

application {
    mainClass = "mod.crend.dynamiccrosshair.compat.updater.UpdaterKt"
}

tasks.run<JavaExec> {
    args(rootDir.resolve("versions").listFiles()!!.map { it.resolve("supported_mods.csv").toString() })
}
