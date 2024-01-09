import com.icemachined.buildutils.*

plugins {
    kotlin("jvm")
    `maven-publish`
}

group = "com.icemachined"
version = "4.2.0"
val cp_suffix = "cp${project.property("python.suffix")}"
val distro_name = "jep-distro-$cp_suffix"
val jepTar: Tar = tasks.create<Tar>(distro_name) {
    into ("jep"){
        from("build/jep", "jep/arm64/$version/$cp_suffix")
        include ("*.*")
    }
    archiveBaseName.set(distro_name)
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
