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
    classpath("de.mannodermaus.gradle.plugins:android-junit5:1.7.1.1")
  }
}

allprojects {
  configRepository()

  tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
      languageVersion = "1.5"
      jvmTarget = Versions.Java.jvmTarget
      allWarningsAsErrors = true
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
        "**/*Response.kt"
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
      ktlint(Versions.ktlint).userData(
        mapOf(
          "indent_size" to "2",
          "continuation_indent_size" to "2"
        )
      )
    }
    java {
      target("**/*.java")
      targetExclude("$buildDir/**/*.java", "bin/**/*.java")
      // licenseHeaderFile(rootProject.file("spotless/license"))
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
