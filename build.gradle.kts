plugins {
  id("com.android.application").apply(false)
  id("com.android.library").apply(false)
  kotlin("android").apply(false)
  id("org.jetbrains.compose") version Versions.compose_jb apply false
  id("com.google.devtools.ksp") version Versions.ksp apply false
  id("com.diffplug.spotless") version Versions.spotless
}

allprojects {
  tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
      jvmTarget = Versions.Java.jvmTarget
      allWarningsAsErrors = false
      freeCompilerArgs = freeCompilerArgs + listOf(
        "-Xallow-unstable-dependencies",
        "-Xskip-prerelease-check"
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
        "**/material3/**/*.kt"
      )
      ktlint(Versions.ktlint).editorConfigOverride(
        mapOf(
          "indent_size" to "2",
          "continuation_indent_size" to "2",
          // rules: https://github.com/pinterest/ktlint/blob/master/README.md#standard-rules
          "disabled_rules" to "filename,trailing-comma"
        )
      )
      trimTrailingWhitespace()
      endWithNewline()
    }
    kotlinGradle {
      target("*.gradle.kts")
      targetExclude(
        "feature/focuskit/**"
      )
      ktlint(Versions.ktlint).editorConfigOverride(
        mapOf(
          "indent_size" to "2",
          "continuation_indent_size" to "2",
          "disabled_rules" to "trailing-comma"
        )
      )
      trimTrailingWhitespace()
      endWithNewline()
    }
  }

  // 剔除livedata，使用flow代替
  configurations.all {
    exclude(group = "androidx.lifecycle", module = "lifecycle-livedata")
  }
}
