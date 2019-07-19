plugins {
    kotlin("jvm")
}

val versionKeycloak: String by project

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation(project(":core"))

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.keycloak:keycloak-spring-boot-starter:$versionKeycloak")
}