package com.example.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.kotlin.dsl.configure

internal fun Project.configureBuildTypes(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    extensionType: ExtensionType
) {
    commonExtension.run {
        buildFeatures {
            buildConfig = true
        }

        val rescuedAnimalsBaseUrl =
            gradleLocalProperties(rootDir, providers).getProperty("RESCUED_ANIMALS_BASE_URL")
        val animalInfoBaseUrl =
            gradleLocalProperties(rootDir, providers).getProperty("ANIMAL_INFO_BASE_URL")
        val serviceKey = gradleLocalProperties(rootDir, providers).getProperty("PUBLIC_SRVC_KEY")
        when (extensionType) {
            ExtensionType.APPLICATION -> {
                extensions.configure<ApplicationExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(
                                rescuedAnimalsBaseUrl,
                                animalInfoBaseUrl,
                                serviceKey
                            )
                        }
//                        create("staging") {
//                            configureStagingBuildType(apiKey)
//                        }
                        release {
//                            isDebuggable = true
//                            signingConfig = signingConfigs.getByName("debug")

                            isMinifyEnabled = true
                            proguardFiles(
                                commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
                                "proguard-rules.pro"
                            )

                            configureReleaseBuildType(
                                commonExtension,
                                rescuedAnimalsBaseUrl,
                                animalInfoBaseUrl,
                                serviceKey
                            )
                        }
                    }
                }
            }

            ExtensionType.LIBRARY -> {
                extensions.configure<LibraryExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(
                                rescuedAnimalsBaseUrl,
                                animalInfoBaseUrl,
                                serviceKey
                            )
                        }
//                        create("staging") {
//                            configureStagingBuildType(apiKey)
//                        }
                        release {
                            configureReleaseBuildType(
                                commonExtension,
                                rescuedAnimalsBaseUrl,
                                animalInfoBaseUrl,
                                serviceKey
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun BuildType.configureDebugBuildType(
    rescuedAnimalsBaseUrl: String,
    animalInfoBaseUrl: String,
    serviceKey: String
) {
    buildConfigField("String", "RESCUED_ANIMALS_BASE_URL", "\"$rescuedAnimalsBaseUrl\"")
    buildConfigField("String", "ANIMAL_INFO_BASE_URL", "\"$animalInfoBaseUrl\"")
    buildConfigField("String", "PUBLIC_SRVC_KEY", "\"$serviceKey\"")
}

//private fun BuildType.configureStagingBuildType(apiKey: String) {
//    buildConfigField("String", "API_KEY", "\"$apiKey\"")
//    buildConfigField("String", "BASE_URL", "\"STAGING_API_URL\"")
//}

private fun BuildType.configureReleaseBuildType(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    rescuedAnimalsBaseUrl: String, animalInfoBaseUrl: String, serviceKey: String
) {
    buildConfigField("String", "RESCUED_ANIMALS_BASE_URL", "\"$rescuedAnimalsBaseUrl\"")
    buildConfigField("String", "ANIMAL_INFO_BASE_URL", "\"$animalInfoBaseUrl\"")
    buildConfigField("String", "PUBLIC_SRVC_KEY", "\"$serviceKey\"")
}