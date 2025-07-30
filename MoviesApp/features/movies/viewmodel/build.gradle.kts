plugins {
    alias(libs.plugins.moviesapp.android.library)
    alias(libs.plugins.moviesapp.arch.viewmodel)
}

android {
    namespace = "com.example.movies.features.movies.viewmodel"
}

dependencies {
    implementation(project(":movie:domain"))
    
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.kotlinx.coroutines)
}