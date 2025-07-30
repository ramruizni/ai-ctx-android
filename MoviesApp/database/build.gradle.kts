plugins {
    alias(libs.plugins.moviesapp.android.library)
    alias(libs.plugins.moviesapp.android.room)
    alias(libs.plugins.moviesapp.hilt)
}

android {
    namespace = "com.example.movies.database"
}

dependencies {
    // DATA SOURCE MODULES GO HERE
    implementation(project(":movie:datasource"))
//    implementation(project(":demo:datasource"))
}