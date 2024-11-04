import com.example.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidRetrofitConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
//            pluginManager.run {
//            }

            dependencies {
                add("implementation", project.libs.findLibrary("retrofit").get())
                add("implementation", project.libs.findLibrary("retrofit.converter.moshi").get())
                add("implementation", project.libs.findLibrary("okhttp.logging.interceptor").get())
                add("implementation", platform(project.libs.findLibrary("okhttp.bom").get()))
            }
        }
    }
}