plugins {
    alias(libs.plugins.convention.android.application)
    alias(libs.plugins.convention.android.application.compose)
    alias(libs.plugins.convention.android.room)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.ramruizni.deepseekpokeappthirteen"

    defaultConfig {
        applicationId = "com.ramruizni.deepseekpokeappthirteen"
        versionCode = 1
        versionName = "1.0.0.${versionCode}"
    }
}

dependencies {
    implementation(project(":database"))
    implementation(project(":navigation"))
    implementation(project(":features:pokemon:view"))
    implementation(project(":features:pokemon:viewmodel"))

    // FEATURE MODULES GO HERE
    implementation(project(":pokemon:domain"))
    implementation(project(":pokemon:infrastructure"))
    implementation(project(":pokemon:datasource"))
    
    // Network dependencies for DI
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation(libs.kotlinx.serialization.json)
}