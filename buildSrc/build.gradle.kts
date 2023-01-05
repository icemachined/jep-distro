plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    // this hack prevents the following bug: https://github.com/gradle/gradle/issues/9770
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.publish.gradle.plugin)
}
