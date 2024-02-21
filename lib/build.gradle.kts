import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

version = "0.0.1"

repositories.mavenCentral()

plugins {
    id("org.jetbrains.kotlin.jvm")
}

val compileKotlinTask = tasks.getByName<KotlinCompile>("compileKotlin") {
    kotlinOptions {
        jvmTarget = Version.jvmTarget
//        freeCompilerArgs = freeCompilerArgs + setOf("-module-name", colonCase(maven.group, maven.id))
    }
}

tasks.getByName<JavaCompile>("compileTestJava") {
    targetCompatibility = Version.jvmTarget
}

tasks.getByName<KotlinCompile>("compileTestKotlin") {
    kotlinOptions.jvmTarget = Version.jvmTarget
}

dependencies {
// todo
}
