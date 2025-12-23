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
include(":dice_roller_5_1")
include(":lemonade_5_2")
include(":calculator_5_3")
include(":art_space_5_4")
include(":test_1")
include(":Affirmations_App_6_1")
include(":courses_6_2")
include(":woof_6_3")
include(":woof_imported")
project(":woof_imported").projectDir = file("woof/app")
include(":superheroes_6_4")
include(":superhero_6_4")
include(":coctale")
include(":dessert_7_1")
include(":unscramble_7_2")
