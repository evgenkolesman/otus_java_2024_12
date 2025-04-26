rootProject.name = "otus_upgrade_proj"
include("hw01-gradle")
include("hw02-gradle")
include("hw02-gradle-libApi")
include("hw02-gradle-libApiUse")
include("hw02-gradle-logging")
include("hw03-generics")
include("hw04-qa")
include("hw05-collections")
include("hw06-annotations")
include("hw06-junit")
include("hw08-gc:demo")
include("hw08-gc:homework")
include("hw09-docker")
include("hw10-aspect")
include("hw11-atm")
include("L15-structuralPatterns:homework")
include("L16-io:homework")
include("L18-jdbc:homework")
include("L18-jdbc:demo")

pluginManagement {
    val jgitver: String by settings
    val dependencyManagement: String by settings
    val springframeworkBoot: String by settings
    val johnrengelmanShadow: String by settings
    val jib: String by settings
    val protobufVer: String by settings
    val spotless: String by settings
    val sonarlint: String by settings


    plugins {
        id("fr.brouillard.oss.gradle.jgitver") version jgitver
        id("io.spring.dependency-management") version dependencyManagement
        id("org.springframework.boot") version springframeworkBoot
        id("com.github.johnrengelman.shadow") version johnrengelmanShadow
        id("com.google.cloud.tools.jib") version jib
        id("com.google.protobuf") version protobufVer
        id("com.diffplug.spotless") version spotless
        id("name.remal.sonarlint") version sonarlint
    }
}