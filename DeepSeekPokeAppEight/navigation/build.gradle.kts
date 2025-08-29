plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.library.compose)
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.ramruizni.deepseekpokeappeight.navigation"
}

dependencies {
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    // Pokemon presentation modules (temporarily commented to avoid circular dependency)
    // implementation(project(":pokemon:view"))
    // implementation(project(":pokemon:viewmodel"))
}