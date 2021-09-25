import org.gradle.api.JavaVersion

object Versions {
  object Kotlin {
    const val lang = "1.5.30"
    const val coroutines = "1.5.2"
    const val serialization = "1.3.0"
  }

  object Java {
    const val jvmTarget = "11"
    val java = JavaVersion.VERSION_11
  }

  const val ksp = "${Kotlin.lang}-1.0.0"
  const val ktlint = "0.41.0"
  const val spotless = "5.12.5"

  const val androidx_test = "1.4.0"
  const val extJUnitVersion = "1.1.3-rc01"
  const val espressoVersion = "3.4.0-rc01"

  const val hilt = "2.38.1"
  const val compose = "1.1.0-alpha04"
  const val activity = "1.3.1"
  const val lifecycle = "2.4.0-alpha03"
  const val lifecycle_compose = "1.0.0-alpha07"
  const val androidx_hilt = "1.0.0"
  const val navigation = "2.4.0-alpha08"
  const val accompanist = "0.17.0"
  const val okhttp = "4.9.1"
  const val room = "2.4.0-alpha04"
  const val appCenter = "4.1.0"
}