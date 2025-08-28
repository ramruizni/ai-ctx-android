plugins {
    alias(libs.plugins.convention.arch.viewmodel)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.ramruizni.deepseekpokeappfive.features.pokemon.viewmodel"
}

dependencies {
    implementation(project(":pokemon:domain"))
    
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}