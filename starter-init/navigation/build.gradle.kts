plugins {
    alias(libs.plugins.starterdemo.android.library)
    alias(libs.plugins.starterdemo.android.library.compose)
    alias(libs.plugins.starterdemo.hilt)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.example.starterdemo.navigation"
}

dependencies {
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    // VIEWS AND VIEW MODELS GO HERE
//    implementation(project(":features:demo:view"))
//    implementation(project(":features:demo:viewmodel"))
}