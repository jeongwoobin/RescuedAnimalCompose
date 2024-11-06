plugins {
    alias(libs.plugins.multi.module.java.library)
}


dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.hilt.core)
}