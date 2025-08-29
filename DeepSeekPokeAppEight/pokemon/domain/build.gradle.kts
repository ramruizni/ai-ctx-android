plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.ramruizni.deepseekpokeappeight.pokemon.domain"
}

dependencies {
    // Coroutines for Flow and suspend functions
    implementation(libs.kotlinx.coroutines)
    
    // No other dependencies - domain layer should be pure business logic
    // Repository implementations will be in infrastructure layer
}