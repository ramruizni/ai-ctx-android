plugins {
    alias(libs.plugins.convention.arch.viewmodel)
}

android {
    namespace = "com.ramruizni.deepseekpokeappseven.features.pokemon.viewmodel"
}

dependencies {
    implementation(project(":pokemon:domain"))
}