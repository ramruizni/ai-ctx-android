plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.room)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.ramruizni.deepseekpokeappsix.database"
}

dependencies {
    // No dependencies - database module should be self-contained
    // Entity classes and DAOs are provided by datasource modules that depend on this module
}