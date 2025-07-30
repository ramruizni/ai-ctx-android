plugins {
    alias(libs.plugins.movieapptwo.android.library)
    alias(libs.plugins.movieapptwo.android.room)
    alias(libs.plugins.movieapptwo.hilt)
}

android {
    namespace = "com.example.movieapptwo.database"
}

dependencies {
    // DATA SOURCE MODULES GO HERE
//    implementation(project(":demo:datasource"))
}