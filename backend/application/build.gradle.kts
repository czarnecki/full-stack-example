apply(plugin = "org.springframework.boot")

plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation(project(":api"))
    implementation(project(":core"))
    implementation(project(":database"))
    implementation(project(":security"))

    implementation("org.springframework.boot:spring-boot-starter")
}