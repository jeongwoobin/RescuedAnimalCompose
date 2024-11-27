plugins {
    alias(libs.plugins.multi.module.android.library)
    alias(libs.plugins.multi.module.android.hilt)
    alias(libs.plugins.multi.module.android.retrofit)
    alias(libs.plugins.multi.module.android.moshi)
    alias(libs.plugins.multi.module.android.room)
}

android {
    namespace = "com.example.data"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // private
    implementation(projects.domain)
}