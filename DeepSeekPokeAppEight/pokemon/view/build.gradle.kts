plugins {
    alias(libs.plugins.convention.android.library.compose)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.ramruizni.deepseekpokeappeight.pokemon.view"
    compileSdk = 36
    
    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(project(":pokemon:domain"))
    implementation(project(":pokemon:viewmodel"))
    // implementation(project(":navigation")) // Temporarily removed to avoid circular dependency
    
    // Compose UI Bundle
    implementation(libs.bundles.androidx.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    
    // Compose State and ViewModels
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    
    // Image Loading
    implementation(libs.coil.compose)
    implementation(libs.coil.base)
    
    // Hilt
    implementation(libs.hilt.android)
    
    // Development Dependencies
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}