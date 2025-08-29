plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.room)
}

android {
    namespace = "com.example.deepseekpokeappeleven.pokemon.datasource"
}

dependencies {
    implementation(libs.bundles.room.datasource)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization.json)
    
    // Networking
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
}