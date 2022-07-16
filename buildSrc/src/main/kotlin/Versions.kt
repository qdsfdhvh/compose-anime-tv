import org.gradle.api.JavaVersion

object Versions {
  object Kotlin {
    const val lang = "1.7.0"
    const val coroutines = "1.6.3"
    const val serialization = "1.3.3"
    const val deateTime = "0.4.0"
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
  const val ktor = "2.0.2"
  const val sqldelight = "2.0.0-alpha03"
  const val buildkonfig = "0.12.0"
  const val napier = "2.6.1"

  const val activity = "1.6.0-alpha05"
  const val lifecycle = "2.6.0-alpha01"
  const val coreKtx = "1.9.0-alpha05"
}