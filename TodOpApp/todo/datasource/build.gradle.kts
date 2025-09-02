plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.room)
}

android {
    namespace = "com.example.todopapp.todo.datasource"
}

dependencies {
    implementation(project(":database"))
}