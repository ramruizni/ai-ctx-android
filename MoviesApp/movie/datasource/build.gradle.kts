plugins {
    alias(libs.plugins.moviesapp.android.library)
    alias(libs.plugins.moviesapp.android.room)
    alias(libs.plugins.moviesapp.hilt)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.example.movies.movie.datasource"
}

dependencies {
    implementation(project(":movie:domain"))
    implementation(project(":movie:infrastructure"))
    
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.kotlinx.coroutines)
    
    implementation(libs.bundles.network)
}