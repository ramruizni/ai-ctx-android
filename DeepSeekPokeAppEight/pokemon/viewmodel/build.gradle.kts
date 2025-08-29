plugins {
    alias(libs.plugins.convention.arch.viewmodel)
}

android {
    namespace = "com.ramruizni.deepseekpokeappeight.pokemon.viewmodel"
}

dependencies {
    implementation(project(":pokemon:domain"))
    
    // ViewModel dependencies already included by convention plugin
    // Hilt dependencies already included by convention plugin
}