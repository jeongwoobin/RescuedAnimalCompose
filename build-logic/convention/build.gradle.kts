plugins {
    `kotlin-dsl`
}

group = "com.example.convention"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "multi.module.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "multi.module.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "multi.module.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "multi.module.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
//        register("androidPresentationUI") {
//            id = "multi.module.android.presentation.ui"
//            implementationClass = "AndroidPresentationUIConventionPlugin"
//        }
        register("javaLibrary") {
            id = "multi.module.java.library"
            implementationClass = "JavaLibraryConventionPlugin"
        }
        register("androidHilt") {
            id = "multi.module.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidRetrofit") {
            id = "multi.module.android.retrofit"
            implementationClass = "AndroidRetrofitConventionPlugin"
        }
        register("androidMoshi") {
            id = "multi.module.android.moshi"
            implementationClass = "AndroidMoshiConventionPlugin"
        }
        register("androidLogger") {
            id = "multi.module.android.logger"
            implementationClass = "AndroidLoggerConventionPlugin"
        }
        register("androidRoom") {
            id = "multi.module.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
    }
}
