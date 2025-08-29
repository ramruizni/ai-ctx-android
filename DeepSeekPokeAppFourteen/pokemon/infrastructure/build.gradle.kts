plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.ramruizni.deepseekpokeappfourteen.pokemon.infrastructure"
}

dependencies {
    implementation(project(":pokemon:domain"))
    implementation(project(":pokemon:datasource"))
    implementation(project(":database"))
    
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
}