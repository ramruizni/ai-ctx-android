import com.android.build.gradle.LibraryExtension
import com.ramruizni.deepseekpokeappsix.convention.ProjectConfig
import com.ramruizni.deepseekpokeappsix.convention.configureKotlinAndroid
import com.ramruizni.deepseekpokeappsix.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("android-library").get().get().pluginId)
                apply(libs.findPlugin("kotlin-android").get().get().pluginId)
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = ProjectConfig.TARGET_SDK
            }

            dependencies {
                add("implementation", libs.findLibrary("androidx.core.ktx").get())
            }

        }
    }
}
