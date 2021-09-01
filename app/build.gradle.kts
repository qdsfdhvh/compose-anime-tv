buildscript {
  repositories {
    google()
    mavenCentral()
  }

  dependencies {
    classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}")
    classpath("de.mannodermaus.gradle.plugins:android-junit5:1.7.1.1")
  }
}

plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("kapt")
  id("com.google.devtools.ksp").version(Versions.ksp)
}

apply(plugin = "dagger.hilt.android.plugin")
apply(plugin = "de.mannodermaus.android-junit5")

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
  sourceSets {
    getByName("main") {
      java.srcDir(File("build/generated/ksp/debug/kotlin"))
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

repositories {
  maven(url = "https://maven.aliyun.com/repository/public")
}

dependencies {
  implementation(project(":core"))
  ksp(project(":compiler:assistedFactory"))
  hiltCompiler()
  network()

  junit5()
  test()
  androidTest()

  // fix not javax.annotation.processing.Generated with dagger2
  // https://github.com/pengrad/jdk9-deps
  compileOnly("com.github.pengrad:jdk9-deps:1.0")

  // image https://github.com/coil-kt/coil
  implementation("io.coil-kt:coil-compose:1.3.2")

  // parse html https://github.com/jhy/jsoup/
  implementation("org.jsoup:jsoup:1.13.1")

  // compose focus https://github.com/qdsfdhvh/compose-focuskit
  implementation("com.github.qdsfdhvh.compose-focuskit:focuskit:0.0.5")
  implementation("com.github.qdsfdhvh.compose-focuskit:player:0.0.5")
}

// 剔除livedata，使用flow代替
configurations.all {
  exclude(group = "androidx.lifecycle", module = "lifecycle-livedata")
}
