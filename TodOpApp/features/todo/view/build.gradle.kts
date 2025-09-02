plugins {
    alias(libs.plugins.convention.arch.view)
}

android {
    namespace = "com.example.todopapp.features.todo.view"
}

dependencies {
    implementation(project(":todo:domain"))
    implementation(project(":features:todo:viewmodel"))
}