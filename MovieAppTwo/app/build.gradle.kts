plugins {
    alias(libs.plugins.movieapptwo.android.application)
    alias(libs.plugins.movieapptwo.android.application.compose)
    alias(libs.plugins.movieapptwo.android.room)
    alias(libs.plugins.movieapptwo.hilt)
}

android {
    namespace = "com.example.movieapptwo"

    defaultConfig {
        applicationId = "com.example.movieapptwo"
        versionCode = 1
        versionName = "1.0.0.${versionCode}"
    }
}

dependencies {
    implementation(project(":database"))
    implementation(project(":navigation"))

    // FEATURE MODULES GO HERE
//    implementation(project(":demo:domain"))
//    implementation(project(":demo:infrastructure"))
//    implementation(project(":demo:datasource"))
}