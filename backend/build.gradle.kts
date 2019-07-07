import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    base
    kotlin("jvm") version "1.3.41" apply false
    id("org.jetbrains.kotlin.plugin.spring") version "1.3.41" apply false
    id("org.springframework.boot") version "2.1.6.RELEASE" apply false
}

allprojects {
    group = "de.trzpiot"
    version = "0.0.1-SNAPSHOT"

    repositories {
        jcenter()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "idea")

    the<DependencyManagementExtension>().apply {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES) {
                bomProperty("kotlin.version", "1.3.41")
            }
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {
    subprojects.forEach {
        archives(it)
    }
}