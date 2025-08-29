plugins {
    alias(libs.plugins.convention.jvm.library)
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    implementation(libs.kotlinx.coroutines)
}