plugins {
    alias(libs.plugins.convention.android.application)
    alias(libs.plugins.convention.android.application.compose)
    alias(libs.plugins.convention.android.room)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.ramruizni.deepseekpokeappfourteen"

    defaultConfig {
        applicationId = "com.ramruizni.deepseekpokeappfourteen"
        versionCode = 1
        versionName = "1.0.0.${versionCode}"
    }
}

dependencies {
    implementation(project(":database"))
    implementation(project(":navigation"))

    // POKEMON FEATURE MODULES
    implementation(project(":pokemon:domain"))
    implementation(project(":pokemon:infrastructure"))
    implementation(project(":pokemon:datasource"))
    implementation(project(":features:pokemon:view"))
    implementation(project(":features:pokemon:viewmodel"))

    // Network dependencies
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp.logging)
    implementation(libs.kotlinx.serialization.json)

    // FEATURE MODULES GO HERE
//    implementation(project(":demo:domain"))
//    implementation(project(":demo:infrastructure"))
//    implementation(project(":demo:datasource"))
}