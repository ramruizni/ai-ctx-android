plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.room)
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.example.deepseekpokeapptwelve.pokemon.datasource"
}

dependencies {
    implementation(project(":pokemon:domain"))
    implementation(libs.kotlinx.serialization.json)
    
    // Network dependencies - added manually since they're not in libs.versions.toml
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
}