package com.example.starterdemo.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureSerializer() {
    with(pluginManager) {
        apply(libs.findPlugin("kotlinx-serialization").get().get().pluginId)
    }
    dependencies {
        add("implementation", libs.findLibrary("kotlinx-serialization-json").get())
    }
}