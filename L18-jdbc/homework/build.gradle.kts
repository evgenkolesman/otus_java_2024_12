dependencies {
    implementation(project(":L18-jdbc:demo"))
    implementation("org.projectlombok:lombok")
    implementation("ch.qos.logback:logback-classic")
    implementation("org.flywaydb:flyway-core")
    implementation("org.postgresql:postgresql")

    annotationProcessor("org.projectlombok:lombok")

}
