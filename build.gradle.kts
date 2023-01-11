import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.icemachined.buildutils.*

plugins {
    kotlin("jvm")
    `maven-publish`
}

group = "com.icemachined"
version = "4.1.0"
val gzFile = layout.buildDirectory.dir("jep")
val gzArtifact = artifacts.add("archives", gzFile) {
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
