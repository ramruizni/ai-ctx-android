plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.ramruizni.deepseekpokeappnine.pokemon.datasource"
}

dependencies {
    implementation(project(":pokemon:domain"))
    
    implementation(libs.bundles.room.datasource)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization.json)
    
    // Network dependencies for PokeAPI
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    
    ksp(libs.room.compiler)
}