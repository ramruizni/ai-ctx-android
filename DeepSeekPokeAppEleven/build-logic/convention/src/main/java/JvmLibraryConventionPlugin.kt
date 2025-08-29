import com.example.deepseekpokeappeleven.convention.configureKotlinJvm
import com.example.deepseekpokeappeleven.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("jetbrains-kotlin-jvm").get().get().pluginId)
            }
            configureKotlinJvm()
        }
    }
}
