dependencies {
	implementation ("com.google.guava:guava")
}


tasks.register("customFatJar", Jar::class) {
	archiveBaseName.set("fat-jar")
	archiveVersion.set("0.1")
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE

	manifest {
		attributes["Main-Class"] = "ru.kolesnikov.hw01gradle.Hw01GradleApplication"
	}

	from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
	with(tasks.jar.get())
}
