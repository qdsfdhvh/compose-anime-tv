rootProject.name = "compose-anime-tv"
include(
  ":app",
  ":core",
  ":compiler:assistedFactory",
  ":compiler:collectCompose",
  ":feature:service",
)

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
    ).forEach { it version "7.0.4" }
  }
}
