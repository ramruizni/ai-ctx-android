plugins {
    alias(libs.plugins.starterdemo.android.application)
    alias(libs.plugins.starterdemo.android.application.compose)
    alias(libs.plugins.starterdemo.android.room)
    alias(libs.plugins.starterdemo.hilt)
}

android {
    namespace = "com.example.starterdemo"

    defaultConfig {
        applicationId = "com.example.starterdemo"
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