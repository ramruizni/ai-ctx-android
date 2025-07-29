plugins {
    alias(libs.plugins.starterdemo.android.library)
    alias(libs.plugins.starterdemo.android.room)
    alias(libs.plugins.starterdemo.hilt)
}

android {
    namespace = "com.example.starterdemo.database"
}

dependencies {
    // DATA SOURCE MODULES GO HERE
//    implementation(project(":demo:datasource"))
}