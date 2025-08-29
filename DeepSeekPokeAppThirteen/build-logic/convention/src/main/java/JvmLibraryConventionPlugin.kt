import com.ramruizni.deepseekpokeappthirteen.convention.configureKotlinJvm
import com.ramruizni.deepseekpokeappthirteen.convention.libs
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
