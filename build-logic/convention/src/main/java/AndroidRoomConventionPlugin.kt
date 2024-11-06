import com.example.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidRoomConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.google.devtools.ksp")
            }

            dependencies {
                add("ksp", project.libs.findLibrary("androidx.room.compiler").get())
                add("implementation", project.libs.findLibrary("androidx.room.ktx").get())
                add("implementation", project.libs.findLibrary("androidx.room.runtime").get())
                add("annotationProcessor", project.libs.findLibrary("androidx.room.runtime").get())
            }
        }
    }
}