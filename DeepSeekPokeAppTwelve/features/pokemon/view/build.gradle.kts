plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.library.compose)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.example.deepseekpokeapptwelve.features.pokemon.view"
}

dependencies {
    implementation(project(":pokemon:domain"))
    implementation(project(":features:pokemon:viewmodel"))
    
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation("io.coil-kt:coil-compose:2.7.0")
    
    // Compose dependencies
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
}