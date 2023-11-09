
val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.9.10"
    id("io.ktor.plugin") version "2.3.5"
    kotlin("plugin.serialization") version "1.9.10"
}

group = "utils.docs"
version = "0.0.1"

application {
    mainClass.set("utils.docs.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-html-builder-jvm")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-css:1.0.0-pre.639")
    implementation("io.ktor:ktor-server-host-common-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("org.jetbrains:markdown:0.5.0")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("com.charleskorn.kaml:kaml:0.55.0")
}
