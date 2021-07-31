import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun config(name: String) = project.findProperty(name).toString()

repositories {
    mavenCentral()
}

plugins {
    java
    kotlin("jvm") version "1.5.21"
    kotlin("plugin.serialization") version "1.4.32"
    id("org.jetbrains.intellij") version "1.1.4"
}

group = "dev.meanmail"
version = "${config("version")}-${config("postfix")}"


dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")
    implementation("io.sentry:sentry:4.3.0")
    testImplementation("junit:junit:4.13.2")
}

intellij {
    pluginName.set(config("pluginName"))
    version.set(
        if (config("ideVersion") == "eap") {
            "LATEST-EAP-SNAPSHOT"
        } else {
            config("ideVersion")
        }
    )
    type.set(config("ideType"))
    val languages = config("languages").split(',').map {
        it.trim().toLowerCase()
    }
    if ("python" in languages) {
        when (type.get()) {
            "PY" -> {
                plugins.add("python")
            }
            "PC" -> {
                plugins.add("PythonCore")
            }
            else -> {
                plugins.add("PythonCore:${config("PythonCore")}")
            }
        }
    }
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

    return notes.joinToString(
        "</p><br><p>",
        prefix = "<p>",
        postfix = "</p><br>"
    ) {
        it.joinToString("<br>")
    } +
            "See the full change notes on the <a href='" +
            config("repository") +
            "/blob/master/CHANGES.md'>github</a>"
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = config("jvmVersion")
        targetCompatibility = config("jvmVersion")
    }
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = config("jvmVersion")
    }
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = config("jvmVersion")
    }
    withType<Wrapper> {
        distributionType = Wrapper.DistributionType.ALL
        gradleVersion = config("gradleVersion")
    }

    test {
        useJUnit()

        maxHeapSize = "1G"
    }

    patchPluginXml {
        pluginDescription.set(file("description.html").readText())
        changeNotes.set(readChangeNotes("CHANGES.md"))
    }

    publishPlugin {
        dependsOn("buildPlugin")
        token.set(System.getenv("PUBLISH_TOKEN"))
        channels.set(listOf(config("publishChannel")))
    }
}
