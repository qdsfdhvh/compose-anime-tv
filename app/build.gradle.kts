plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("kapt")
  id("com.google.devtools.ksp").version(Versions.ksp)
  id("dagger.hilt.android.plugin")
  id("de.mannodermaus.android-junit5")
}

kapt {
  correctErrorTypes = true
  mapDiagnosticLocations = true
  arguments {
    arg("dagger.formatGeneratedSource", "disabled")
    arg("dagger.fastInit", "enabled")
    arg("dagger.experimentalDaggerErrorMessages", "enabled")
  }
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
  sourceSets {
    getByName("debug") {
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
    jniLibs {
      pickFirsts.addAll(
        listOf(
          "lib/arm64-v8a/libc++_shared.so",
          "lib/armeabi-v7a/libc++_shared.so",
          "lib/x86/libc++_shared.so",
          "lib/x86_64/libc++_shared.so",
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
  implementation(project(":feature:service"))
  implementation(project(":feature:analytics"))
  implementation(project(":focuskit"))
  implementation(project(":focuskit-player"))
  ksp(project(":compiler:assistedFactory"))

  hilt()
  compose()
  android()
  kotlinCoroutines()
  room()

  junit5()
  test()
  androidTest()

  // fix not javax.annotation.processing.Generated with dagger2
  // https://github.com/pengrad/jdk9-deps
  compileOnly("com.github.pengrad:jdk9-deps:1.0")

  // hidden api by pass https://github.com/tiann/FreeReflection
  implementation("com.github.tiann:FreeReflection:3.1.0")
}
