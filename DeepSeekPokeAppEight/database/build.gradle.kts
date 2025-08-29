plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.room)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.ramruizni.deepseekpokeappeight.database"
}

dependencies {
    // Note: Database module should not depend on datasource modules
    // Datasource modules depend on database module instead
}