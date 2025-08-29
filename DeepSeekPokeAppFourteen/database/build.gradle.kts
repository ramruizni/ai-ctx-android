plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.room)
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.ramruizni.deepseekpokeappfourteen.database"
}

dependencies {
    // DATA SOURCE MODULES GO HERE
    implementation(libs.kotlinx.serialization.json)
//    implementation(project(":pokemon:datasource"))
//    implementation(project(":demo:datasource"))
}