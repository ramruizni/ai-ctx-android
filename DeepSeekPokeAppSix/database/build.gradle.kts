plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.room)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.ramruizni.deepseekpokeappsix.database"
}

dependencies {
    // DATA SOURCE MODULES GO HERE
//    implementation(project(":demo:datasource"))
}