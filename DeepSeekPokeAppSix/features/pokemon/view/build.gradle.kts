plugins {
    alias(libs.plugins.convention.arch.view)
}

android {
    namespace = "com.ramruizni.deepseekpokeappsix.features.pokemon.view"
}

dependencies {
    implementation(project(":pokemon:domain"))
    implementation(project(":features:pokemon:viewmodel"))
    
    // Coil for image loading
    implementation(libs.coil.compose)
    
    // Compose Material 3
    implementation(libs.androidx.material3)
    
    // Pull to refresh
    implementation(libs.androidx.compose.material)
    
    // Coil networking for image loading
    implementation(libs.coil.network.okhttp)
}