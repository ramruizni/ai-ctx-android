plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.library.compose)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.ramruizni.deepseekpokeappfourteen.features.pokemon.view"
}

dependencies {
    implementation(project(":pokemon:domain"))
    implementation(project(":features:pokemon:viewmodel"))
    
    implementation(libs.bundles.android.ui)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)
}