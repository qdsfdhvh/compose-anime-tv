import org.gradle.api.JavaVersion

object Versions {
  object Kotlin {
    const val lang = "1.5.31"
    const val coroutines = "1.5.2"
    const val serialization = "1.3.0"
  }

  object Java {
    const val jvmTarget = "11"
    val java = JavaVersion.VERSION_11
  }

  const val ksp = "${Kotlin.lang}-1.0.0"
  const val kotlinPoet = "1.10.1"
  const val ktlint = "0.41.0"
  const val spotless = "5.12.5"

  const val androidx_test = "1.4.0"
  const val extJUnitVersion = "1.1.3-rc01"
  const val espressoVersion = "3.4.0-rc01"

  const val koin = "3.1.5"
  const val compose = "1.0.5"
  const val activity = "1.4.0"
  const val lifecycle = "2.4.0"
  const val coreKtx = "1.7.0"
  const val navigation = "2.4.0-beta02"
  const val navigationCompose = "1.0.0-beta01"
  const val accompanist = "0.20.2"
  const val paging = "3.1.0"
  const val pagingCompose = "1.0.0-alpha14"
  const val okhttp = "4.9.3"
  const val room = "2.4.0-beta02"
  const val appCenter = "4.1.0"
  const val coil = "1.4.0"
  const val timber = "4.7.1"
}