repositories {
    mavenCentral()
//    maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
    maven("https://central.sonatype.com/repository/maven-snapshots/")
}

plugins {
    `kotlin-dsl`
}

dependencies {
    implementation("com.github.kepocnhh:GradleExtension.Core:0.6.1-SNAPSHOT")
}
