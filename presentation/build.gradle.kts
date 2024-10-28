plugins {
    alias(libs.plugins.multi.module.android.presentation.ui)
}

android {
    namespace = "com.example.presentation"
}

dependencies {
    // private
    implementation(projects.domain)
}