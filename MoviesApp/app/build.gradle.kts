plugins {
    alias(libs.plugins.moviesapp.android.application)
    alias(libs.plugins.moviesapp.android.application.compose)
    alias(libs.plugins.moviesapp.android.room)
    alias(libs.plugins.moviesapp.hilt)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.example.movies"

    defaultConfig {
        applicationId = "com.example.movies"
        versionCode = 1
        versionName = "1.0.0.${versionCode}"
    }
}

dependencies {
    implementation(project(":database"))
    implementation(project(":navigation"))

    // FEATURE MODULES GO HERE
    implementation(project(":movie:domain"))
    implementation(project(":movie:infrastructure"))
    implementation(project(":movie:datasource"))
    implementation(project(":features:movies:view"))
    implementation(project(":features:movies:viewmodel"))
//    implementation(project(":demo:domain"))
//    implementation(project(":demo:infrastructure"))
//    implementation(project(":demo:datasource"))

    // Network dependencies
    implementation(libs.bundles.network)
}