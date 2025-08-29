plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.room)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.ramruizni.deepseekpokeappseven.database"
}

dependencies {
    // DOMAIN MODULES GO HERE
    implementation(project(":pokemon:domain"))
}