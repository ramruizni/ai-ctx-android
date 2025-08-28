plugins {
    alias(libs.plugins.convention.android.application)
    alias(libs.plugins.convention.android.application.compose)
    alias(libs.plugins.convention.android.room)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.ramruizni.deepseekpokeappsix"

    defaultConfig {
        applicationId = "com.ramruizni.deepseekpokeappsix"
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