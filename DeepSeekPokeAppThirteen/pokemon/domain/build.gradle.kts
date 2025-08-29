plugins {
    alias(libs.plugins.convention.android.library)
}

android {
    namespace = "com.ramruizni.deepseekpokeappthirteen.pokemon.domain"
}

dependencies {
    implementation(libs.kotlinx.coroutines)
}