buildscript {
  repositories {
    google()
  }

  dependencies {
    classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}")
  }
}

plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("kapt")
}

apply(plugin = "dagger.hilt.android.plugin")

android {
  compileSdk = AndroidSdk.compile
  buildToolsVersion = AndroidSdk.buildTools
  defaultConfig {
    applicationId = "com.seiko.tv.anime"
    minSdk = AndroidSdk.min
    targetSdk = AndroidSdk.target
    versionCode = 1
    versionName = "1.0.0"
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
  buildTypes {
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
  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = Versions.compose
  }
  packagingOptions {
    resources {
      excludes.addAll(
        listOf(
          "META-INF/AL2.0",
          "META-INF/LGPL2.1",
        )
      )
    }
  }
}

dependencies {
  compose()
  activity()
  hilt()
  lifecycle()
  kotlinCoroutines()
  navigation()
  accompanist()

  junit4()
  mockito()
  androidTest()

  // compose color https://github.com/PatilSiddhesh/Holi
  implementation("com.siddroid:holi:0.0.6")
}