import com.example.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidMoshiConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
//            pluginManager.run {
//            }

            dependencies {
                add("implementation", project.libs.findLibrary("moshi.kotlin").get())
            }
        }
    }
}