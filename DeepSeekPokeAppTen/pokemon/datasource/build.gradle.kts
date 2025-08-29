plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.room)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.example.deepseekpokeappten.pokemon.datasource"
}

dependencies {
    implementation(project(":database"))
    implementation(project(":pokemon:domain"))
    implementation(libs.bundles.room.datasource)
    implementation(libs.bundles.http.client)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines)
}