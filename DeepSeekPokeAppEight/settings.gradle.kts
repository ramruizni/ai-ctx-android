pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "DeepSeekPokeAppEight"
include(":app")
include(":navigation")
include(":database")

// Pokemon modules (all layers)
include(":pokemon:view")
include(":pokemon:viewmodel")
include(":pokemon:domain")
include(":pokemon:datasource")
include(":pokemon:infrastructure")
