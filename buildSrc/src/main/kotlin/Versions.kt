import org.gradle.api.JavaVersion

object Versions {
  object Kotlin {
    const val lang = "1.7.0"
    const val coroutines = "1.6.3"
    const val serialization = "1.3.3"
  }

  object Java {
    const val jvmTarget = "11"
    val java = JavaVersion.VERSION_11
  }

  const val ksp = "${Kotlin.lang}-1.0.6"
  const val ktlint = "0.46.1"
  const val spotless = "6.8.0"

  const val koin = "3.1.6"
  const val compose_jb = "1.2.0-alpha01-dev741" // TODO focus error, androidx 1.0.5 ok
  const val compose = "1.3.0-alpha01"
  const val composeMaterial = "1.0.0-alpha14"
  const val activity = "1.6.0-alpha05"
  const val lifecycle = "2.6.0-alpha01"
  const val coreKtx = "1.9.0-alpha05"
  // const val navigation = "2.4.0-beta02"
  // const val navigationCompose = "1.0.0-beta01"
  const val accompanist = "0.24.13-rc"
  // const val paging = "3.2.0-alpha01"
  // const val pagingCompose = "1.0.0-alpha15"
  const val okhttp = "4.10.0"
  const val room = "2.5.0-alpha02"
  // const val appCenter = "4.1.0"
  const val coil = "2.1.0"
  // const val timber = "4.7.1"
  const val napier = "2.6.1"
}