plugins {
    alias(libs.plugins.convention.arch.view)
}

android {
    namespace = "com.example.deepseekpokeappten.features.pokemon.view"
}

dependencies {
    implementation(project(":features:pokemon:viewmodel"))
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
}