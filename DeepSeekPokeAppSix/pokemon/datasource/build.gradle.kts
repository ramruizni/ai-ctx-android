plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.ramruizni.deepseekpokeappsix.pokemon.datasource"
}

dependencies {
    implementation(project(":pokemon:domain"))
    implementation(project(":database"))
    
    // Network
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.okhttp.logging)
    implementation(libs.kotlinx.serialization.json)
    
    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
}