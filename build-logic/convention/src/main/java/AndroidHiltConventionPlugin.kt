import com.example.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.google.devtools.ksp")
                apply("dagger.hilt.android.plugin")
            }

            dependencies {
                add("ksp", project.libs.findLibrary("hilt.android.compiler").get())
                add("implementation", project.libs.findLibrary("hilt.android").get())
                add("testImplementation", project.libs.findLibrary("hilt.android.testing").get())
                add("kspTest", project.libs.findLibrary("hilt.android.compiler").get())
            }
        }
    }
}