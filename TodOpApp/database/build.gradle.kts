plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.room)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.example.todopapp.database"
}

dependencies {
    // No dependencies - database module should be independent
}