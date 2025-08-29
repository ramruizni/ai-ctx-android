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

rootProject.name = "DeepSeekPokeAppEleven"
include(":app")
include(":navigation")
include(":database")

// VIEWS AND VIEW MODELS GO HERE
include(":features:pokemon:view")
include(":features:pokemon:viewmodel")
//include(":features:demo:view")
//include(":features:demo:viewmodel")

// FEATURE MODULES GO HERE
include(":pokemon:domain")
include(":pokemon:infrastructure")
include(":pokemon:datasource")
//include(":demo:domain")
//include(":demo:infrastructure")
//include(":demo:datasource")
