plugins {
    alias(libs.plugins.multi.module.android.library)
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