import com.example.movies.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class ArchViewConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("moviesapp.android.library")
                apply("moviesapp.android.library.compose")
                apply("moviesapp.hilt")
            }

            dependencies {
                add("implementation", libs.findBundle("android.ui").get())

                add("debugImplementation", libs.findLibrary("androidx.ui.tooling").get())
                add("debugImplementation", libs.findLibrary("androidx.ui.test.manifest").get())
            }
        }
    }
}