plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.example.deepseekpokeappten.pokemon.infrastructure"
}

dependencies {
    implementation(project(":pokemon:domain"))
    implementation(project(":pokemon:datasource"))
    implementation(project(":database"))
    implementation(libs.kotlinx.coroutines)
}