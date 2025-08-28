plugins {
    alias(libs.plugins.convention.jvm.library)
}

android {
    namespace = "com.ramruizni.deepseekpokeappfive.pokemon.domain"
}

dependencies {
    implementation(libs.kotlinx.coroutines)
}