plugins {
    alias(libs.plugins.moviesapp.arch.viewmodel)
}

android {
    namespace = "com.example.movies.features.moviedetail.viewmodel"
}

dependencies {
    implementation(project(":movie:domain"))
}