import com.example.deepseekpokeapptwelve.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class ArchViewModelConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("convention.android.library")
                apply("convention.hilt")
            }

            dependencies {
                add("implementation", libs.findLibrary("androidx.lifecycle.viewmodel.ktx").get())
            }
        }
    }
}