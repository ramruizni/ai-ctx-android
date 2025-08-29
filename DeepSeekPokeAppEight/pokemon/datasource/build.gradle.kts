plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.room)
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.ramruizni.deepseekpokeappeight.pokemon.datasource"
}

dependencies {
    implementation(project(":database"))

    // Network
    implementation(libs.bundles.network)
    implementation(libs.kotlinx.serialization.json)
    
    // Room
    implementation(libs.bundles.room.datasource)
    ksp(libs.room.compiler)
}