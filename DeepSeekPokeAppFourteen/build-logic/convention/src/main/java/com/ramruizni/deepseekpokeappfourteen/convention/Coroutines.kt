package com.ramruizni.deepseekpokeappfourteen.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureCoroutines() {
    dependencies {
        add("implementation", libs.findLibrary("kotlinx.coroutines").get())
        add("testImplementation", libs.findLibrary("kotlinx.coroutines.test").get())
    }
}