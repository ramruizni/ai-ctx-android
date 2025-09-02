plugins {
    alias(libs.plugins.convention.android.application)
    alias(libs.plugins.convention.android.application.compose)
    alias(libs.plugins.convention.android.room)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.example.todopapp"

    defaultConfig {
        applicationId = "com.example.todopapp"
        versionCode = 1
        versionName = "1.0.0.${versionCode}"
    }
}

dependencies {
    implementation(project(":database"))
    implementation(project(":navigation"))
    
    // FEATURE VIEW MODULES
    implementation(project(":features:todo:view"))
    implementation(project(":features:todo:viewmodel"))

    // FEATURE MODULES GO HERE
    implementation(project(":todo:domain"))
    implementation(project(":todo:infrastructure"))
    implementation(project(":todo:datasource"))
}