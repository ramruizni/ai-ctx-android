plugins {
    id("convention.arch.view")
}

android {
    namespace = "com.ramruizni.deepseekpokeappfour.features.pokemon.view"
}

dependencies {
    implementation(project(":features:pokemon:viewmodel"))
    
    // Image Loading
    implementation(libs.coil.compose)
}