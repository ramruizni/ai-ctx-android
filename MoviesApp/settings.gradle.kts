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

rootProject.name = "MoviesApp"
include(":app")
include(":navigation")
include(":database")

// VIEWS AND VIEW MODELS GO HERE
include(":features:movies:view")
include(":features:movies:viewmodel")
include(":features:movie-detail:view")
include(":features:movie-detail:viewmodel")
//include(":features:demo:view")
//include(":features:demo:viewmodel")

// FEATURE MODULES GO HERE
include(":movie:domain")
include(":movie:infrastructure")
include(":movie:datasource")
//include(":demo:domain")
//include(":demo:infrastructure")
//include(":demo:datasource")
