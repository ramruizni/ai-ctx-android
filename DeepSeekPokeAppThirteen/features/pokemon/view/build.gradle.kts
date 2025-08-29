plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.library.compose)
}

android {
    namespace = "com.ramruizni.deepseekpokeappthirteen.features.pokemon.view"
}

dependencies {
    implementation(project(":pokemon:domain"))
    implementation(project(":features:pokemon:viewmodel"))
    
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
}