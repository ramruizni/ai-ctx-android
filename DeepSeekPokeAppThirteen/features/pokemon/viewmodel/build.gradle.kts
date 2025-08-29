plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.ramruizni.deepseekpokeappthirteen.features.pokemon.viewmodel"
}

dependencies {
    implementation(project(":pokemon:domain"))
    
    implementation(libs.kotlinx.coroutines)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
}