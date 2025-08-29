plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.ramruizni.deepseekpokeappseven.pokemon.infrastructure"
}

dependencies {
    implementation(project(":database"))
    implementation(project(":pokemon:domain"))
    implementation(project(":pokemon:datasource"))
    
    // Coroutines
    implementation(libs.kotlinx.coroutines)
    
    // Hilt
    implementation(libs.hilt.core)
}