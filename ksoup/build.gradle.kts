plugins {
  kotlin("multiplatform")
  kotlin("native.cocoapods")
  id("com.android.library")
  id("com.google.devtools.ksp")
}

version = "1.0.0"
group = "com.seiko.tv.anime.ksoup"

kotlin {
  android()
  jvm {
    compilations.all {
      kotlinOptions.jvmTarget = Versions.Java.jvmTarget
    }
  }
  ios {
    // setupNativeConfig()
  }
  sourceSets {
    val commonMain by getting {
      dependencies {
      }
    }
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test"))
      }
    }
    val jsoupMain by creating {
      dependsOn(commonMain)
      dependencies {
        api("org.jsoup:jsoup:1.14.3")
      }
    }
    val androidMain by getting {
      dependsOn(jsoupMain)
    }
    val jvmMain by getting {
      dependsOn(jsoupMain)
    }
    val iosMain by getting {
      dependencies {
      }
    }
  }
  cocoapods {
    summary = "Html Parser"
    homepage = "Link to the Shared Module homepage"
    ios.deploymentTarget = "14.1"
    osx.deploymentTarget = "12.2.1"
    framework {
      baseName = "Ksoup"
    }
    // pod(
    //   name = "SwiftSoup",
    //   version = ">=2.0.0",
    // )
    pod(
      name = "GDataXML-HTML",
      version = "~> 1.4.1",
    )
  }
}

android {
  namespace = "com.seiko.tv.anime.ksoup"
  compileSdk = AndroidSdk.compile
  defaultConfig {
    minSdk = AndroidSdk.min
  }
  compileOptions {
    sourceCompatibility = Versions.Java.java
    targetCompatibility = Versions.Java.java
  }
}

// fun org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget.setupNativeConfig() {
//   compilations["main"].kotlinOptions.freeCompilerArgs += listOf(
//     "-Xallocator=mimalloc",
//     "-Xruntime-logs=gc=info",
//     "-Xexport-kdoc"
//   )
//   binaries {
//     /*all {
//         binaryOptions["memoryModel"] = "experimental"
//         binaryOptions["freezing"] = "disabled"
//     }*/
//   }
// }
