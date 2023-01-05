import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.icemachined.buildutils.*

plugins {
    kotlin("jvm")
}

group = "com.icemachined"
version = "4.1.0"
val gzFile = layout.projectDirectory.file("~/jep-distro/jep-distro.tar.gz")
val gzArtifact = artifacts.add("archives", gzFile.asFile) {
    type = "tar.gz"
    //builtBy("tar")
}

configurePublishing()
