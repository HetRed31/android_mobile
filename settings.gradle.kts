pluginManagement {
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

rootProject.name = "lab4"
include(":app")
include(":app:4_1")
include(":happybirthday_4_2")
include(":composearticle_4_3")
include(":taskmanager_4_4")
include(":compose_quadrant_4_5")
include(":business_card_app_4_6")
