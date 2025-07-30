plugins {
    alias(libs.plugins.moviesapp.android.library)
    alias(libs.plugins.moviesapp.android.library.compose)
    alias(libs.plugins.moviesapp.arch.view)
}

android {
    namespace = "com.example.movies.features.moviedetail.view"
}

dependencies {
    implementation(project(":movie:domain"))
    implementation(project(":features:movie-detail:viewmodel"))
    
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.coil.compose)
}