import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import java.util.*

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kobweb.application)
}

group = "com.varabyte.kobweb_showcase_site"
version = "1.0-SNAPSHOT"

// URL resolution: CI Env > showcase.config.properties > hardcoded fallback
val configFile = rootProject.file("showcase.config.properties")
val configProps = Properties().apply {
    if (configFile.exists()) configFile.inputStream().use { load(it) }
}
val dataUrl: String =
    System.getenv("SHOWCASE_DATA_URL")
        ?: configProps.getProperty("showcase.data.url")
        ?: "https://raw.githubusercontent.com/varabyte/kobweb-showcase-db/refs/heads/main/resources/db/showcased-sites.json"

kobweb {
    app {
        index {
            description.set("Kobweb Showcase — sites built with Kobweb")
        }
        globals.put("SHOWCASE_DATA_URL", dataUrl)
    }
    pagesPackage.set("com.varabyte.kobweb_showcase_site.ui.pages")
}

kotlin {
    configAsKobwebApplication("kobweb_showcase_site")

    sourceSets {
        jsMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.html.core)
            implementation(libs.kobweb.core)
            implementation(libs.kobweb.silk)
            implementation(libs.silk.icons.fa)
            implementation(project(":worker"))
        }
    }
}
