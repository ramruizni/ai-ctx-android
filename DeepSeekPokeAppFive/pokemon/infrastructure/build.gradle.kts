plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.ramruizni.deepseekpokeappfive.pokemon.infrastructure"
}

dependencies {
    implementation(project(":pokemon:domain"))
    implementation(project(":pokemon:datasource"))
    
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}