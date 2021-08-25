import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories

fun Project.configRepository() {
  repositories {
    google()
    mavenCentral()
    maven("https://jitpack.io")
  }
}

fun DependencyHandlerScope.hilt() {
  api("com.google.dagger:hilt-android", Versions.hilt)
  api("androidx.hilt:hilt-work", Versions.androidx_hilt)
}

fun DependencyHandlerScope.hiltCompiler() {
  kapt("com.google.dagger:hilt-android-compiler", Versions.hilt)
  kapt("androidx.hilt:hilt-compiler", Versions.androidx_hilt)
  // Fix Caused by: java.lang.IllegalStateException: The Hilt Android Gradle plugin ...
  implementation("com.google.dagger:hilt-android", Versions.hilt)
}

fun DependencyHandlerScope.compose() {
  api("androidx.compose.ui:ui", Versions.compose)
  api("androidx.compose.ui:ui-tooling", Versions.compose)
  api("androidx.compose.foundation:foundation", Versions.compose)
  api("androidx.compose.animation:animation", Versions.compose)
  api("androidx.compose.material:material", Versions.compose)
  api("androidx.compose.material:material-icons-core", Versions.compose)
  api("androidx.compose.material:material-icons-extended", Versions.compose)
  api("androidx.constraintlayout:constraintlayout-compose", Versions.constraintLayout)
}

fun DependencyHandlerScope.androidx() {
  api("androidx.core:core-ktx:1.6.0")
  api("androidx.activity:activity-ktx", Versions.activity)
  api("androidx.activity:activity-compose", Versions.activity)
  api("com.google.android.exoplayer:exoplayer", Versions.exoplayer)
  api("com.google.android.exoplayer:extension-okhttp", Versions.exoplayer)
}

fun DependencyHandlerScope.lifecycle() {
  api("androidx.lifecycle:lifecycle-runtime-ktx", Versions.lifecycle)
  api("androidx.lifecycle:lifecycle-viewmodel-ktx", Versions.lifecycle)
  api("androidx.lifecycle:lifecycle-viewmodel-savedstate", Versions.lifecycle)
  api("androidx.lifecycle:lifecycle-common-java8", Versions.lifecycle)
  api("androidx.lifecycle:lifecycle-viewmodel-compose", Versions.lifecycle_compose)
}

fun DependencyHandlerScope.kotlinCoroutines() {
  api("org.jetbrains.kotlinx:kotlinx-coroutines-core", Versions.Kotlin.coroutines)
  api("org.jetbrains.kotlinx:kotlinx-coroutines-android", Versions.Kotlin.coroutines)
}

fun DependencyHandlerScope.accompanist() {
  api("com.google.accompanist:accompanist-coil", Versions.accompanist)
  api("com.google.accompanist:accompanist-insets", Versions.accompanist)
  api("com.google.accompanist:accompanist-systemuicontroller", Versions.accompanist)
}

fun DependencyHandlerScope.navigation() {
  api("androidx.navigation:navigation-compose", Versions.navigation)
  api("androidx.hilt:hilt-navigation-compose:1.0.0-alpha03")
}

fun DependencyHandlerScope.network() {
  implementation("com.squareup.okhttp3:okhttp", Versions.okhttp)
  implementation("com.squareup.okhttp3:logging-interceptor", Versions.okhttp)
  // 🐂🍺 https://github.com/Tlaster/Hson
  implementation("com.github.Tlaster:Hson:0.1.4")
}

fun DependencyHandlerScope.junit5() {
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.1")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.1")
}

fun DependencyHandlerScope.test() {
  testImplementation("org.mockito:mockito-core:3.11.2")
  testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test", Versions.Kotlin.coroutines)
}

fun DependencyHandlerScope.androidTest() {
  androidTestImplementation("androidx.arch.core:core-testing:2.1.0")
  androidTestImplementation("androidx.test:core", Versions.androidx_test)
  androidTestImplementation("androidx.test:runner", Versions.androidx_test)
  androidTestImplementation("androidx.test.ext:junit", Versions.extJUnitVersion)
  androidTestImplementation("androidx.test.espresso:espresso-core", Versions.espressoVersion)
  androidTestImplementation("androidx.compose.ui:ui-test", Versions.compose)
}