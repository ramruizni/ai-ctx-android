plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.room)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.example.deepseekpokeappten.database"
}

dependencies {
    // DATA SOURCE MODULES GO HERE
    // Note: No datasource dependencies to avoid circular dependencies
}