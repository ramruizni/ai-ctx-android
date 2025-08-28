plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.ramruizni.deepseekpokeappsix.pokemon.domain"
}

dependencies {
    implementation(libs.kotlinx.coroutines)
}