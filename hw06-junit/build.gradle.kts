plugins {
    id ("com.github.johnrengelman.shadow")
}

dependencies {
    implementation ("ch.qos.logback:logback-classic")
}

tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        archiveBaseName.set("junitOtus")
        archiveVersion.set("0.1")
        archiveClassifier.set("")
        manifest {
            attributes(mapOf("Main-Class" to "ru.otus.junit.RunnerClass"))
        }
    }

    build {
        dependsOn(shadowJar)
    }
}

