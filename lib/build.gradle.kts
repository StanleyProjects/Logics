import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import sp.gx.core.Maven
import sp.gx.core.colonCase

version = "0.0.1"

val maven =
    Maven.Artifact(
        group = "com.github.kepocnhh",
        id = rootProject.name,
    )

repositories.mavenCentral()

plugins {
    id("org.jetbrains.kotlin.jvm")
}

val compileKotlinTask =
    tasks.getByName<KotlinCompile>("compileKotlin") {
        kotlinOptions {
            jvmTarget = Version.JVM_TARGET
            freeCompilerArgs = freeCompilerArgs + setOf("-module-name", colonCase(maven.group, maven.id))
        }
    }

tasks.getByName<JavaCompile>("compileTestJava") {
    targetCompatibility = Version.JVM_TARGET
}

tasks.getByName<KotlinCompile>("compileTestKotlin") {
    kotlinOptions.jvmTarget = Version.JVM_TARGET
}

dependencies {
// todo
}
