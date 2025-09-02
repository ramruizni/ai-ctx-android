import com.example.todopapp.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidWorkManagerConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("convention.hilt")
            }

            dependencies {
                "implementation"(libs.findLibrary("androidx.work.runtime.ktx").get())
                "implementation"(libs.findLibrary("hilt.ext.work").get())
                "implementation"(libs.findLibrary("hilt.ext.common").get())
                "ksp"(libs.findLibrary("hilt.ext.compiler").get())
            }
        }
    }
}