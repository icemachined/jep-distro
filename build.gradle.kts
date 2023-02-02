import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.icemachined.buildutils.*

plugins {
    kotlin("jvm")
    `maven-publish`
}

group = "com.icemachined"
version = "4.1.1"
val jepTar: Tar = tasks.create<Tar>("jep-distro-cp${project.property("python.suffix")}") {
    into ("jep"){
        from("build/jep")
        include ("*.*")
    }
    archiveBaseName.set("jep-distro-cp${project.property("python.suffix")}")
    compression = Compression.GZIP
}
val gzArtifact = artifacts.add("archives", jepTar)

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifact(gzArtifact)
        }
    }
}

configurePublishing()
