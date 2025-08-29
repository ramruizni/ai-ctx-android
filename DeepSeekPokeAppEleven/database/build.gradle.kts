plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.room)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.example.deepseekpokeappeleven.database"
}

dependencies {
    // DATA SOURCE MODULES GO HERE
    api(project(":pokemon:datasource"))
//    implementation(project(":demo:datasource"))
}