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

rootProject.name = "TodOpApp"
include(":app")
include(":navigation")
include(":database")

// VIEWS AND VIEW MODELS GO HERE
include(":features:todo:view")
include(":features:todo:viewmodel")

// FEATURE MODULES GO HERE
include(":todo:domain")
include(":todo:infrastructure")
include(":todo:datasource")
