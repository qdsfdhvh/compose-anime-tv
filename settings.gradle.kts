rootProject.name = "compose-anime-tv"
include(":app", ":core")
include(":compiler:assistedFactory")
include(":feature:service", ":feature:analytics")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
  repositories {
    gradlePluginPortal()
    google()
  }
  plugins {
    arrayOf(
      id("com.android.application"),
      id("com.android.library")
    ).forEach { it version "7.1.0-alpha11" }
  }
}
