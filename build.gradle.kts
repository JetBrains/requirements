import org.jetbrains.intellij.tasks.PatchPluginXmlTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.50"
    id("org.jetbrains.intellij") version "0.4.5"
}

group = "ru.meanmail"
version = "2019.5"

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
    version = "2019.2"
    setPlugins("PythonCore:2019.2.192.5728.98")
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
    } + "See the full change notes on the " +
            "<a href='https://github.com/meanmail/requirements'>github</a>"
}

tasks.withType<PatchPluginXmlTask> {
    setPluginDescription(file("Description.txt").readText())
    setChangeNotes(readChangeNotes("ChangeNotes.md"))
}
