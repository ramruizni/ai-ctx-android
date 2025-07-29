plugins {
    `kotlin-dsl`
}

group = "com.example.starterdemo.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.room.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("android-application-compose") {
            id = "starterdemo.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("android-application") {
            id = "starterdemo.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("android-library-compose") {
            id = "starterdemo.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("android-library") {
            id = "starterdemo.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("android-room") {
            id = "starterdemo.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("android-workManager") {
            id = "starterdemo.android.workManager"
            implementationClass = "AndroidWorkManagerConventionPlugin"
        }
        register("hilt") {
            id = "starterdemo.hilt"
            implementationClass = "HiltConventionPlugin"
        }
        register("jvm-library") {
            id = "starterdemo.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }

        // Architecture
        register("arch-view") {
            id = "starterdemo.arch.view"
            implementationClass = "ArchViewConventionPlugin"
        }
        register("arch-viewmodel") {
            id = "starterdemo.arch.viewmodel"
            implementationClass = "ArchViewModelConventionPlugin"
        }
    }
}