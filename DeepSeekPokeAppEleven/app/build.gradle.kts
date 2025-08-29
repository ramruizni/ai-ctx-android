plugins {
    alias(libs.plugins.convention.android.application)
    alias(libs.plugins.convention.android.application.compose)
    alias(libs.plugins.convention.android.room)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.example.deepseekpokeappeleven"

    defaultConfig {
        applicationId = "com.example.deepseekpokeappeleven"
        versionCode = 1
        versionName = "1.0.0.${versionCode}"
    }
}

dependencies {
    implementation(project(":database"))
    implementation(project(":navigation"))

    // Networking (needed for DI)
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.kotlinx.serialization.json)

    // FEATURE MODULES GO HERE
    implementation(project(":pokemon:domain"))
    implementation(project(":pokemon:infrastructure"))
    implementation(project(":pokemon:datasource"))
    implementation(project(":features:pokemon:view"))
//    implementation(project(":demo:domain"))
//    implementation(project(":demo:infrastructure"))
//    implementation(project(":demo:datasource"))
}