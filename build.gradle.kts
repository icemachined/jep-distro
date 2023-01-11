import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.icemachined.buildutils.*

plugins {
    kotlin("jvm")
    `maven-publish`
}

group = "com.icemachined"
version = "4.1.0"
val gzFile = layout.buildDirectory.file("jep-distro-$version.tar.gz")
val gzArtifact = artifacts.add("archives", gzFile.get().asFile) {
    type = "gz"
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifact(gzArtifact)
        }
    }
}

configurePublishing()
