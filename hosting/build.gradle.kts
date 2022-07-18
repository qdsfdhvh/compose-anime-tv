plugins {
  kotlin("multiplatform")
  id("com.android.library")
  id("org.jetbrains.compose")
  kotlin("plugin.parcelize")
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
      dependencies {
        implementation(projects.core)
        implementation(projects.feature.service)
      }
    }
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test"))
      }
    }
    val androidMain by getting {
      dependencies {
        implementation("androidx.compose.ui:ui-tooling-preview:${Versions.compose}")
        // Player https://github.com/google/ExoPlayer
        implementation("com.google.android.exoplayer:exoplayer:2.18.0")
      }
    }
  }
}

android {
  namespace = "${Package.applicationId}.hosting"
  compileSdk = AndroidSdk.compile
  defaultConfig {
    minSdk = AndroidSdk.min
  }
  compileOptions {
    sourceCompatibility = Versions.Java.java
    targetCompatibility = Versions.Java.java
  }
}
