plugins {
    alias(libs.plugins.convention.android.library)
}

android {
    namespace = "com.example.todopapp.todo.infrastructure"
}

dependencies {
    implementation(project(":todo:domain"))
    implementation(project(":todo:datasource"))
}