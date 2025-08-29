plugins {
    alias(libs.plugins.convention.arch.view)
}

android {
    namespace = "com.ramruizni.deepseekpokeappseven.features.pokemon.view"
}

dependencies {
    implementation(project(":features:pokemon:viewmodel"))
    implementation(project(":pokemon:domain"))
    
    // Image Loading
    implementation(libs.coil.compose)
}