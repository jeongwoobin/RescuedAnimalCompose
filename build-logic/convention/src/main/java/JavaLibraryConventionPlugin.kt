import com.example.convention.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

internal class JavaLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("java-library")
                apply("org.jetbrains.kotlin.jvm")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            extensions.configure<JavaPluginExtension>
            {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }

            extensions.configure<KotlinProjectExtension>
            {
                jvmToolchain(17)
            }

            dependencies {
                add("implementation", project.libs.findLibrary("kotlinx.serialization.json").get())
            }
        }
    }
}