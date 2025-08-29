plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.library.compose)
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.ramruizni.deepseekpokeappthirteen.navigation"
}

dependencies {
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    // VIEWS AND VIEW MODELS GO HERE
    implementation(project(":features:pokemon:view"))
    implementation(project(":features:pokemon:viewmodel"))
}