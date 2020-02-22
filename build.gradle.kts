import org.jetbrains.intellij.tasks.PatchPluginXmlTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.61"
    id("org.jetbrains.intellij") version "0.4.16"
}

group = "ru.meanmail"
version = project.properties["version"].toString()

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

tasks.withType<Wrapper> {
    distributionType = Wrapper.DistributionType.ALL
    gradleVersion = project.properties["gradleVersion"].toString()
}

tasks.test {
    useJUnitPlatform()

    maxHeapSize = "1G"
}

intellij {
    pluginName = project.properties["pluginName"].toString()
    version = if (project.properties["eap"].toString() == "true") {
        "LATEST-EAP-SNAPSHOT"
    } else {
        project.properties["IdeVersion"].toString()
    }
    setPlugins(project.properties["pythonPluginVersion"].toString())
}

fun readChangeNotes(pathname: String): String {
    val lines = file(pathname).readLines()

    val notes: MutableList<MutableList<String>> = mutableListOf()

    var note: MutableList<String>? = null

    for (line in lines) {
        if (line.startsWith('#')) {
            if (notes.size == 3) {
                break
            }
            note = mutableListOf()
            notes.add(note)
            val header = line.trimStart('#')
            note.add("<b>$header</b>")
        } else if (line.isNotBlank()) {
            note?.add(line)
        }
    }

    return notes.joinToString("</p><br><p>", prefix = "<p>",
            postfix = "</p><br>") {
        it.joinToString("<br>")
    } +
            "See the full change notes on the <a href='" +
            project.properties["repository"] +
            "/blob/master/ChangeNotes.md'>github</a>"
}

tasks.withType<PatchPluginXmlTask> {
    setPluginDescription(file("Description.html").readText())
    setChangeNotes(readChangeNotes("ChangeNotes.md"))
    setSinceBuild(project.properties["IdeVersion"].toString())
}
