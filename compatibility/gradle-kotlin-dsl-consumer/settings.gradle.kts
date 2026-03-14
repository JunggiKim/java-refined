val consumerKotlinVersion = providers.gradleProperty("consumerKotlinVersion").orElse("1.9.25").get()

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        kotlin("jvm") version consumerKotlinVersion
    }
}

rootProject.name = "java-refined-kotlin-dsl-consumer"
