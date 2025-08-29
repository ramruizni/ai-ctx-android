import com.android.build.gradle.LibraryExtension
import com.example.deepseekpokeapptwelve.convention.configureAndroidCompose
import com.example.deepseekpokeapptwelve.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(libs.findPlugin("android.library").get().get().pluginId)
            pluginManager.apply(libs.findPlugin("kotlin.compose").get().get().pluginId)

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)
        }
    }
}
