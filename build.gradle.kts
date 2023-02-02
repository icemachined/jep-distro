import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.icemachined.buildutils.*

plugins {
    kotlin("jvm")
    `maven-publish`
}

group = "com.icemachined"
version = "4.1.1"
val distro_name = "jep-distro-cp${project.property("python.suffix")}"
val jepTar: Tar = tasks.create<Tar>(distro_name) {
    into ("jep"){
        from("build/jep")
        include ("*.*")
    }
    compression = Compression.GZIP
}
val gzArtifact = artifacts.add("archives", jepTar)

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifact(gzArtifact)
            artifactId = distro_name
        }
    }
}

configurePublishing()
