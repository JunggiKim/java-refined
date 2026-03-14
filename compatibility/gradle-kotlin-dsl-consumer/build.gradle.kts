val refinedVersion = providers.gradleProperty("refinedVersion").orElse("1.1.0").get()

plugins {
    kotlin("jvm")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.github.junggikim:java-refined:$refinedVersion")
    implementation("io.github.junggikim:java-refined-kotlin:$refinedVersion")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
