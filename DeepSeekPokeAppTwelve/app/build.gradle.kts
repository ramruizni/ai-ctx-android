plugins {
    alias(libs.plugins.convention.android.application)
    alias(libs.plugins.convention.android.application.compose)
    alias(libs.plugins.convention.android.room)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.example.deepseekpokeapptwelve"

    defaultConfig {
        applicationId = "com.example.deepseekpokeapptwelve"
        versionCode = 1
        versionName = "1.0.0.${versionCode}"
    }
}

dependencies {
    implementation(project(":database"))
    implementation(project(":navigation"))

    // FEATURE MODULES GO HERE
    implementation(project(":pokemon:domain"))
    implementation(project(":pokemon:infrastructure"))
    implementation(project(":pokemon:datasource"))
    implementation(project(":features:pokemon:view"))
    implementation(project(":features:pokemon:viewmodel"))
    
    // Network dependencies for DI modules
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation(libs.kotlinx.serialization.json)
}