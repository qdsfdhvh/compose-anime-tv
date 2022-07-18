plugins {
  id("com.android.application")
  id("org.jetbrains.compose")
  kotlin("android")
}

android {
  compileSdk = AndroidSdk.compile
  buildToolsVersion = AndroidSdk.buildTools
  defaultConfig {
    applicationId = Package.applicationId
    minSdk = AndroidSdk.min
    targetSdk = AndroidSdk.target
    versionCode = Package.versionCode
    versionName = Package.versionName
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
  signingConfigs {
    getByName("debug") {
      storeFile = rootProject.file("secrets/debug-keystore.jks")
      storePassword = "123456"
      keyAlias = "compose-anime-tv"
      keyPassword = "123456"
    }
  }
  buildTypes {
    debug {
      signingConfig = signingConfigs.getByName("debug")
    }
    release {
      isMinifyEnabled = false
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )
    }
  }
  compileOptions {
    sourceCompatibility = Versions.Java.java
    targetCompatibility = Versions.Java.java
  }
  packagingOptions {
    resources {
      excludes.addAll(
        listOf(
          "META-INF/AL2.0",
          "META-INF/LGPL2.1"
        )
      )
    }
    jniLibs {
      pickFirsts.addAll(
        listOf(
          "lib/arm64-v8a/libc++_shared.so",
          "lib/armeabi-v7a/libc++_shared.so",
          "lib/x86/libc++_shared.so",
          "lib/x86_64/libc++_shared.so"
        )
      )
    }
  }
}

dependencies {
  implementation(projects.core)
  implementation(projects.hosting)
  // Di
  implementation("io.insert-koin:koin-android:${Versions.koin}")
}
