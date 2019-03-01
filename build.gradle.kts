import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.tasks.wrapper.Wrapper

plugins {
    java
    kotlin("jvm") version "1.2.30"
    id("org.jetbrains.intellij") version "0.3.4"
}

group = "ru.meanmail"
version = "2019.1"

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Wrapper> {
    distributionType = Wrapper.DistributionType.ALL
    gradleVersion = project.properties["gradleVersion"].toString()
}

intellij {
    pluginName = "requirements"
    version = "2018.3.4"
}
