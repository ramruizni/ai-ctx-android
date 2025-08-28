import com.ramruizni.deepseekpokeappsix.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class ArchViewConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("convention.android.library")
                apply("convention.android.library.compose")
                apply("convention.hilt")
            }

            dependencies {
                add("implementation", libs.findBundle("android.ui").get())

                add("debugImplementation", libs.findLibrary("androidx.ui.tooling").get())
                add("debugImplementation", libs.findLibrary("androidx.ui.test.manifest").get())
            }
        }
    }
}