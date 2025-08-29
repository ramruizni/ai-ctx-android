plugins {
    alias(libs.plugins.convention.arch.view)
}

android {
    namespace = "com.ramruizni.deepseekpokeappnine.features.pokemon.view"
}

dependencies {
    implementation(project(":pokemon:domain"))
    implementation(project(":features:pokemon:viewmodel"))
    
    implementation("io.coil-kt:coil-compose:2.7.0")
}