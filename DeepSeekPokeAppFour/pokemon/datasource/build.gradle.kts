plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.room)
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.ramruizni.deepseekpokeappfour.pokemon.datasource"
}

dependencies {
    implementation(project(":pokemon:domain"))
    api(libs.bundles.room.datasource)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization.json)
    
    ksp(libs.room.compiler)
}