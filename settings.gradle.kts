rootProject.name = "compose-anime-tv"
include(":app", ":core")
include(
  ":compiler:assistedFactory",
  ":compiler:smallScreen",
)
include(
  ":feature:service",
  ":feature:analytics",
  ":focuskit",
  ":focuskit-player",
)

val focuskitRoot = "feature${File.separator}focuskit"
project(":focuskit").projectDir = file("$focuskitRoot${File.separator}focuskit")
project(":focuskit-player").projectDir = file("$focuskitRoot${File.separator}player")

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
    ).forEach { it version "7.1.0-alpha12" }
  }
}
