plugins {
  id("com.android.application") apply false
  id("com.android.library") apply false
  id("com.diffplug.spotless").version(Versions.spotless)
  id("com.gradleup.auto.manifest").version("1.0.4")
}

buildscript {
  repositories {
    google()
    mavenCentral()
  }
  dependencies {
    classpath(kotlin("gradle-plugin", version = Versions.Kotlin.lang))
    classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}")
    classpath("de.mannodermaus.gradle.plugins:android-junit5:1.8.0.0")
  }
}

allprojects {
  configRepository()

  tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
      jvmTarget = Versions.Java.jvmTarget
      allWarningsAsErrors = false
      freeCompilerArgs = listOf(
        "-Xopt-in=kotlin.RequiresOptIn",
        "-Xallow-unstable-dependencies"
      )
    }
  }

  apply(plugin = "com.diffplug.spotless")
  spotless {
    kotlin {
      target("**/*.kt")
      targetExclude(
        "$buildDir/**/*.kt",
        "bin/**/*.kt",
        "buildSrc/**/*.kt",
        "**/*Response.kt",
      )
      ktlint(Versions.ktlint).userData(
        mapOf(
          "indent_size" to "2",
          "continuation_indent_size" to "2"
        )
      )
      // licenseHeaderFile(rootProject.file("spotless/license"))
    }
    kotlinGradle {
      target("*.gradle.kts")
      targetExclude(
        "feature/focuskit/**"
      )
      ktlint(Versions.ktlint).userData(
        mapOf(
          "indent_size" to "2",
          "continuation_indent_size" to "2"
        )
      )
    }
  }

  // 剔除livedata，使用flow代替
  configurations.all {
    exclude(group = "androidx.lifecycle", module = "lifecycle-livedata")
  }
}

autoManifest {
  packageName.set(Package.applicationId)
}
