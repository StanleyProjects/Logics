import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import sp.gx.core.Badge
import sp.gx.core.Markdown
import sp.gx.core.Maven
import sp.gx.core.asFile
import sp.gx.core.buildDir
import sp.gx.core.check
import sp.gx.core.create

version = "0.0.1"

val maven = Maven.Artifact(
    group = "com.github.kepocnhh",
    id = rootProject.name,
)

repositories.mavenCentral()

plugins {
    id("org.jetbrains.kotlin.jvm")
}

val compileKotlinTask = tasks.getByName<KotlinCompile>("compileKotlin") {
    kotlinOptions {
        jvmTarget = Version.jvmTarget
        freeCompilerArgs = freeCompilerArgs + setOf("-module-name", maven.moduleName())
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

"unstable".also { variant ->
    val version = "${version}u-SNAPSHOT"
    tasks.create("check", variant, "Readme") {
        doLast {
            val badge = Markdown.image(
                text = "version",
                url = Badge.url(
                    label = "version",
                    message = version,
                    color = "2962ff",
                ),
            )
            val expected = setOf(
                badge,
                Markdown.link("Maven", Maven.Snapshot.url(maven, version)),
                "implementation(\"${maven.moduleName(version)}\")",
            )
            rootDir.resolve("README.md").check(
                expected = expected,
                report = buildDir()
                    .dir("reports/analysis/readme")
                    .asFile("index.html"),
            )
        }
    }
    // todo
}
