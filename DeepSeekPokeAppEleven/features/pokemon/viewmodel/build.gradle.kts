plugins {
    alias(libs.plugins.convention.arch.viewmodel)
}

android {
    namespace = "com.example.deepseekpokeappeleven.features.pokemon.viewmodel"
}

dependencies {
    implementation(project(":pokemon:domain"))
}