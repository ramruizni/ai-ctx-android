plugins {
    alias(libs.plugins.moviesapp.android.library)
    alias(libs.plugins.moviesapp.android.library.compose)
    alias(libs.plugins.moviesapp.hilt)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.example.movies.navigation"
}

dependencies {
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    // VIEWS AND VIEW MODELS GO HERE
    implementation(project(":features:movies:view"))
    implementation(project(":features:movies:viewmodel"))
    implementation(project(":features:movie-detail:view"))
    implementation(project(":features:movie-detail:viewmodel"))
//    implementation(project(":features:demo:view"))
//    implementation(project(":features:demo:viewmodel"))
}