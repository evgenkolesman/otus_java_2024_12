dependencies {
    implementation(project(":L18-jdbc:demo"))
    implementation("org.projectlombok:lombok")
    implementation("ch.qos.logback:logback-classic")
    implementation("org.flywaydb:flyway-core")
    implementation("org.postgresql:postgresql")
    implementation("org.yaml:snakeyaml:2.0")

    annotationProcessor("org.projectlombok:lombok")

}
