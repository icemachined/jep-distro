/**
 * Gradle configuration for making a release to maven central
 */

@file:Suppress("FILE_WILDCARD_IMPORTS", "TOO_LONG_FUNCTION")

package com.icemachined.buildutils

import io.github.gradlenexus.publishplugin.NexusPublishExtension
import io.github.gradlenexus.publishplugin.NexusPublishPlugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.api.publish.maven.tasks.AbstractPublishToMaven
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.*
import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform
import org.gradle.plugins.signing.SigningExtension
import org.gradle.plugins.signing.SigningPlugin

/**
 * configuration for publishing to Nexus (will be used in build.gradle script)
 */
fun Project.configurePublishing() {
    // If present, set properties from env variables. If any are absent, release will fail.
    System.getenv("OSSRH_USERNAME")?.let {
        extra.set("sonatypeUsername", it)
    }
    System.getenv("OSSRH_PASSWORD")?.let {
        extra.set("sonatypePassword", it)
    }
    System.getenv("PGP_SEC")?.let {
        extra.set("signingKey", it)
    }
    System.getenv("PGP_KEYID")?.let {
        extra.set("signing.keyId", it)
    }
    System.getenv("PGP_SEC_FILE")?.let {
        extra.set("signing.secretKeyRingFile", it)
    }
    System.getenv("PGP_PASSWORD")?.let {
        extra.set("signing.password", it)
    }

    if (this == rootProject) {
        apply<NexusPublishPlugin>()
        if (hasProperty("sonatypeUsername")) {
            configureNexusPublishing()
        }
    }

    apply<MavenPublishPlugin>()
    apply<SigningPlugin>()

    configurePublications()

    if (hasProperty("signingKey") || hasProperty("signing.secretKeyRingFile")) {
        configureSigning()
    }

    // https://kotlinlang.org/docs/multiplatform-publish-lib.html#avoid-duplicate-publications
    // `configureNexusPublishing` adds sonatype publication tasks inside `afterEvaluate`.
    afterEvaluate {
        val publicationsFromMainHost = listOf(
            "maven",
            "metadata"
        )
        configure<PublishingExtension> {
            publications.matching { it.name in publicationsFromMainHost }.all {
                val targetPublication = this@all
                tasks.withType<AbstractPublishToMaven>()
                    .matching { it.publication == targetPublication }
                    .configureEach {
                        onlyIf {
                            // main publishing CI job is executed on Linux host
                            DefaultNativePlatform.getCurrentOperatingSystem().isLinux.apply {
                                if (!this) {
                                    logger.lifecycle("Publication ${(it as AbstractPublishToMaven).publication.name} is skipped on current host")
                                }
                            }
                        }
                    }
            }
        }
    }
}

private fun Project.configurePublications() {
    val dokkaJar: Jar = tasks.create<Jar>("dokkaJar") {
        group = "documentation"
        archiveClassifier.set("javadoc")
        from(tasks.findByName("dokkaHtml"))
    }
    configure<PublishingExtension> {
        repositories {
            mavenLocal()
        }
        publications.withType<MavenPublication>().forEach { publication ->
            publication.artifact(dokkaJar)
            publication.pom {
                logger.lifecycle("Configuring ${project.name} publication")
                name.set(project.name)
                description.set(project.description ?: project.name)
                url.set("https://github.com/icemachined/jep-distro")
                licenses {
                    license {
                        name.set("zlib/libpng License")
                        url.set("http://zlib.net/zlib_license.html")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("icemachined")
                        name.set("Dmitry Morozovsky")
                        email.set("icemachined.github@gmail.com")
                    }
                }
                scm {
                    url.set("https://github.com/icemachined/jep-distro")
                    connection.set("scm:git:git://github.com/icemachined/jep-distro.git")
                }
            }
        }
    }
}

private fun Project.configureSigning() {
    configure<SigningExtension> {
        if(hasProperty("signingKey")) {
            useInMemoryPgpKeys(property("signingKey") as String?, property("signing.password") as String?)
        } else {
            logger.lifecycle("Use secfile: ${property("signing.secretKeyRingFile")}")
        }
        logger.lifecycle("The following publications are getting signed: ${extensions.getByType<PublishingExtension>().publications.map { it.name }}")
        sign(*extensions.getByType<PublishingExtension>().publications.toTypedArray())
    }
}

private fun Project.configureNexusPublishing() {
    configure<NexusPublishExtension> {
        repositories {
            sonatype {
                // only for users registered in Sonatype after 24 Feb 2021
                nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
                snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
                username.set(property("sonatypeUsername") as String)
                password.set(property("sonatypePassword") as String)
            }
        }
    }
}
