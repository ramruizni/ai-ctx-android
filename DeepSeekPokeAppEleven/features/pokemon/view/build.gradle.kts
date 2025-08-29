plugins {
    alias(libs.plugins.convention.arch.view)
}

android {
    namespace = "com.example.deepseekpokeappeleven.features.pokemon.view"
}

dependencies {
    implementation(project(":features:pokemon:viewmodel"))
    implementation(project(":pokemon:domain"))
    implementation(libs.coil.compose)
}