plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.room)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.example.deepseekpokeapptwelve.database"
}

dependencies {
    implementation(project(":pokemon:datasource"))
}