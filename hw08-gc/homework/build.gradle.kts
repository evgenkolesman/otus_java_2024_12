plugins {
    id ("com.github.johnrengelman.shadow")
}

dependencies {
    implementation ("ch.qos.logback:logback-classic")
}

tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        archiveBaseName.set("gradleHelloWorld")
        archiveVersion.set("0.1")
        archiveClassifier.set("")
        manifest {
            attributes(mapOf("Main-Class" to "ru.calculator.CalcDemo"))
        }
    }

    build {
        dependsOn(shadowJar)
    }
}