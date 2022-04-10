plugins {
  `kotlin-dsl`
}

repositories {
  mavenCentral()
  google()
}

dependencies {
  implementation("com.android.tools.build:gradle:7.0.4")
  api(kotlin("gradle-plugin", version = "1.6.10"))
}
