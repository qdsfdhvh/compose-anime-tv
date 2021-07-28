import org.gradle.api.JavaVersion

object Versions {
  object Kotlin {
    const val lang = "1.5.10"
    const val coroutines = "1.5.0"
  }

  object Java {
    const val jvmTarget = "11"
    val java = JavaVersion.VERSION_11
  }

  const val ktlint = "0.41.0"
  const val spotless = "5.12.5"

  const val androidx_test = "1.4.0"
  const val extJUnitVersion = "1.1.3-rc01"
  const val espressoVersion = "3.4.0-rc01"

  const val hilt = "2.37"
  const val compose = "1.0.0-rc02"
  const val activity = "1.3.0-rc02"
  const val lifecycle = "2.4.0-alpha02"
  const val lifecycle_compose = "1.0.0-alpha07"
  const val androidx_hilt = "1.0.0"
  const val navigation = "2.4.0-alpha05"
  const val accompanist = "0.14.0"
}