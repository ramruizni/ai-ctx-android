plugins {
    alias(libs.plugins.convention.arch.viewmodel)
}

android {
    namespace = "com.example.todopapp.features.todo.viewmodel"
}

dependencies {
    implementation(project(":todo:domain"))
}