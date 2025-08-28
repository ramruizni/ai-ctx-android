plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.room)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.ramruizni.deepseekpokeappfour.database"
}

dependencies {
    // DATA SOURCE MODULES GO HERE
    implementation(project(":pokemon:datasource"))
//    implementation(project(":demo:datasource"))
}