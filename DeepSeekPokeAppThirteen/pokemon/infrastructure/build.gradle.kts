plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.ramruizni.deepseekpokeappthirteen.pokemon.infrastructure"
}

dependencies {
    implementation(project(":pokemon:domain"))
    implementation(project(":pokemon:datasource"))
    
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization.json)
}