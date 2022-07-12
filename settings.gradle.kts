pluginManagement {
  repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
  }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://jitpack.io")
  }
}

rootProject.name = "compose-anime-tv"

include(
  ":core",
  ":feature:service",
  ":app:hosting",
  ":app:android",
  ":app:desktop",
  ":app:ios",
)

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
