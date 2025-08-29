plugins {
    alias(libs.plugins.convention.android.application)
    alias(libs.plugins.convention.android.application.compose)
    alias(libs.plugins.convention.android.room)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.example.deepseekpokeappten"

    defaultConfig {
        applicationId = "com.example.deepseekpokeappten"
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

    // HTTP CLIENT
    implementation(libs.bundles.http.client)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.kotlinx.serialization.json)
}