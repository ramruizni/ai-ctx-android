plugins {
    alias(libs.plugins.moviesapp.jvm.library)
    alias(libs.plugins.moviesapp.hilt)
}

dependencies {
    implementation(libs.hilt.core)
}