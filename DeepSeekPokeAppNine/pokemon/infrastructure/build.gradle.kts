plugins {
    alias(libs.plugins.convention.android.library)
}

android {
    namespace = "com.ramruizni.deepseekpokeappnine.pokemon.infrastructure"
}

dependencies {
    implementation(project(":pokemon:domain"))
    implementation(project(":pokemon:datasource"))
    
    implementation(libs.kotlinx.coroutines)
}