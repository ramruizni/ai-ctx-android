plugins {
    alias(libs.plugins.convention.arch.viewmodel)
}

android {
    namespace = "com.example.deepseekpokeappten.features.pokemon.viewmodel"
}

dependencies {
    implementation(project(":pokemon:domain"))
}