# Logics
It is a business logic or UI level state holder.

---

## Snapshot

![version](https://img.shields.io/static/v1?label=version&message=0.1.3-SNAPSHOT&labelColor=212121&color=2962ff&style=flat)

- [Maven](https://s01.oss.sonatype.org/content/repositories/snapshots/com/github/kepocnhh/Logics/0.1.3-SNAPSHOT)
- [Documentation](https://StanleyProjects.github.io/Logics/doc/0.1.3-SNAPSHOT)

### Build
```
$ gradle lib:assembleSnapshotJar
```

### Import
```kotlin
repositories {
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    implementation("com.github.kepocnhh:Logics:0.1.3-SNAPSHOT")
}
```

---

## Unstable

> GitHub [0.2.0u-SNAPSHOT](https://github.com/StanleyProjects/Logics/releases/tag/0.2.0u-SNAPSHOT) release
>
> Maven [metadata](https://central.sonatype.com/repository/maven-snapshots/com/github/kepocnhh/Logics/maven-metadata.xml)

### Build
```
$ gradle lib:assembleUnstableJar
```

### Import
```kotlin
repositories {
    maven("https://central.sonatype.com/repository/maven-snapshots")
}

dependencies {
    implementation("com.github.kepocnhh:Logics:0.2.0u-SNAPSHOT")
}
```

---
