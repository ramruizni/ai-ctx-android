plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.ramruizni.deepseekpokeappfive.pokemon.datasource"
}

dependencies {
    implementation(project(":pokemon:domain"))
    implementation(project(":database"))
    implementation(libs.bundles.network)
    implementation(libs.bundles.room.datasource)
    implementation(libs.kotlinx.serialization.json)
    
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    ksp(libs.room.compiler)
}