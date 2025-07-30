plugins {
    alias(libs.plugins.moviesapp.jvm.library)
}

dependencies {
    implementation(project(":movie:domain"))
    implementation(libs.hilt.core)
    implementation(libs.kotlinx.coroutines)
}