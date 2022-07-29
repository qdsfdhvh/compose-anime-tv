plugins {
  kotlin("multiplatform")
  id("com.android.library")
  id("com.google.devtools.ksp")
  id("app.cash.sqldelight").version(Versions.sqldelight)
  kotlin("plugin.serialization").version(Versions.Kotlin.lang)
}

kotlin {
  android()
  jvm {
    compilations.all {
      kotlinOptions.jvmTarget = Versions.Java.jvmTarget
    }
  }
  ios()
  sourceSets {
    val commonMain by getting {
      kotlin.srcDir("src/commonMain/third")
      dependencies {
        implementation(projects.core)
        implementation(projects.ksoup)

        // Network
        implementation("io.ktor:ktor-client-logging:${Versions.ktor}")
        implementation("io.ktor:ktor-client-content-negotiation:${Versions.ktor}")

        // Db
        implementation("app.cash.sqldelight:coroutines-extensions:${Versions.sqldelight}")
      }
    }
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test"))
      }
    }
    val androidMain by getting {
      dependencies {
        implementation("io.ktor:ktor-client-okhttp:${Versions.ktor}")
        implementation("app.cash.sqldelight:android-driver:${Versions.sqldelight}")
      }
    }
    val jvmMain by getting {
      dependencies {
        implementation("io.ktor:ktor-client-okhttp:${Versions.ktor}")
        implementation("app.cash.sqldelight:sqlite-driver:${Versions.sqldelight}")
      }
    }
    val iosMain by getting {
      dependencies {
        implementation("io.ktor:ktor-client-darwin:${Versions.ktor}")
        implementation("app.cash.sqldelight:native-driver:${Versions.sqldelight}")
      }
    }
  }
}

android {
  namespace = "${Package.applicationId}.feature.service"
  compileSdk = AndroidSdk.compile
  defaultConfig {
    minSdk = AndroidSdk.min
  }
  compileOptions {
    sourceCompatibility = Versions.Java.java
    targetCompatibility = Versions.Java.java
  }
}

sqldelight {
  database("AppDatabase") {
    packageName = "${Package.applicationId}.db"
    sourceFolders = listOf("sqldelight/app")
  }
}
