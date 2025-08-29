plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.ramruizni.deepseekpokeappeight.pokemon.infrastructure"
}

dependencies {
    implementation(project(":pokemon:datasource"))
    implementation(project(":pokemon:domain"))
    implementation(project(":database"))
}