plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation(project(":core"))

    implementation("org.springframework.boot:spring-boot-starter-data-neo4j")
}