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
      freeCompilerArgs += listOf(
        "-opt-in=kotlin.RequiresOptIn",
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
      ktlint(Versions.ktlint).editorConfigOverride(
        mapOf(
          "indent_size" to "2",
          "continuation_indent_size" to "2"
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
          "continuation_indent_size" to "2"
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
