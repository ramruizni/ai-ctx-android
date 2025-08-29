plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.example.deepseekpokeapptwelve.features.pokemon.viewmodel"
}

dependencies {
    implementation(project(":pokemon:domain"))
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.kotlinx.coroutines)
}