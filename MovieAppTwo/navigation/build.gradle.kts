plugins {
    alias(libs.plugins.movieapptwo.android.library)
    alias(libs.plugins.movieapptwo.android.library.compose)
    alias(libs.plugins.movieapptwo.hilt)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.example.movieapptwo.navigation"
}

dependencies {
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    // VIEWS AND VIEW MODELS GO HERE
//    implementation(project(":features:demo:view"))
//    implementation(project(":features:demo:viewmodel"))
}