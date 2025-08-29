plugins {
    alias(libs.plugins.convention.android.application)
    alias(libs.plugins.convention.android.application.compose)
    alias(libs.plugins.convention.android.room)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.ramruizni.deepseekpokeappeight"

    defaultConfig {
        applicationId = "com.ramruizni.deepseekpokeappeight"
        versionCode = 1
        versionName = "1.0.0.${versionCode}"
    }
}

dependencies {
    implementation(project(":database"))
    implementation(project(":navigation"))

    // Pokemon feature modules (domain/data layer)
    implementation(project(":pokemon:domain"))
    implementation(project(":pokemon:infrastructure"))
    implementation(project(":pokemon:datasource"))
    
    // Pokemon presentation layer modules are included via navigation module
}