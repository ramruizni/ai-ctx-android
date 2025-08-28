import com.android.build.api.dsl.ApplicationExtension
import com.ramruizni.deepseekpokeappsix.convention.ProjectConfig
import com.ramruizni.deepseekpokeappsix.convention.configureBuildTypes
import com.ramruizni.deepseekpokeappsix.convention.configureKotlinAndroid
import com.ramruizni.deepseekpokeappsix.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("android-application").get().get().pluginId)
                apply(libs.findPlugin("kotlin-android").get().get().pluginId)
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)

                defaultConfig {
                    targetSdk = ProjectConfig.TARGET_SDK

                    vectorDrawables {
                        useSupportLibrary = true
                    }
                }

                configureBuildTypes(this)

                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }
            }

            dependencies {
                add("implementation", libs.findBundle("android.ui").get())

                add("debugImplementation", libs.findLibrary("androidx.ui.tooling").get())
            }
        }
    }
}