import com.android.build.api.dsl.ApplicationExtension
import com.example.convention.ExtensionType
import com.example.convention.configureBuildTypes
import com.example.convention.configureKotlinAndroid
import com.example.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                defaultConfig {
                    applicationId = libs.findVersion("projectApplicationId").get().toString()
                    targetSdk = libs.findVersion("projectTargetSdkVersion").get().toString().toInt()
                    versionCode = libs.findVersion("projectVersionCode").get().toString().toInt()
                    versionName = libs.findVersion("projectVersionName").get().toString()

                    setProperty(
                        "archivesBaseName",
                        "${applicationId}_${versionName}_VC${versionCode}_${
                            SimpleDateFormat(
                                "yyyyMMdd_HHmm",
                                Locale.KOREAN
                            ).format(Date())
                        }"
                    )
                }

                configureKotlinAndroid(this)

                configureBuildTypes(
                    commonExtension = this, extensionType = ExtensionType.APPLICATION
                )
            }
        }
    }
}
