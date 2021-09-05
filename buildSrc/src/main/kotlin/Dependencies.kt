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
  implementation("com.google.dagger:hilt-android", Versions.hilt)
  implementation("androidx.hilt:hilt-work", Versions.androidx_hilt)
  kapt("com.google.dagger:hilt-android-compiler", Versions.hilt)
  kapt("androidx.hilt:hilt-compiler", Versions.androidx_hilt)
}

fun DependencyHandlerScope.compose() {
  implementation("androidx.compose.ui:ui", Versions.compose)
  implementation("androidx.compose.ui:ui-tooling", Versions.compose)
  implementation("androidx.compose.foundation:foundation", Versions.compose)
  implementation("androidx.compose.animation:animation", Versions.compose)
  implementation("androidx.compose.material:material", Versions.compose)
  implementation("androidx.compose.material:material-icons-core", Versions.compose)
  implementation("androidx.compose.material:material-icons-extended", Versions.compose)
  implementation("androidx.navigation:navigation-compose", Versions.navigation)
  implementation("androidx.hilt:hilt-navigation-compose:1.0.0-alpha03")
  implementation("com.google.accompanist:accompanist-insets", Versions.accompanist)
  implementation("com.google.accompanist:accompanist-systemuicontroller", Versions.accompanist)
  implementation("com.github.qdsfdhvh.compose-focuskit:focuskit:0.0.5")
  implementation("com.github.qdsfdhvh.compose-focuskit:player:0.0.5")
}

fun DependencyHandlerScope.android() {
  lifecycle()
  implementation("androidx.core:core-ktx:1.6.0")
  implementation("androidx.activity:activity-ktx", Versions.activity)
  implementation("androidx.activity:activity-compose", Versions.activity)
  implementation("io.coil-kt:coil-compose:1.3.2")
}

fun DependencyHandlerScope.lifecycle() {
  implementation("androidx.lifecycle:lifecycle-runtime-ktx", Versions.lifecycle)
  implementation("androidx.lifecycle:lifecycle-viewmodel-ktx", Versions.lifecycle)
  implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate", Versions.lifecycle)
  implementation("androidx.lifecycle:lifecycle-common-java8", Versions.lifecycle)
  implementation("androidx.lifecycle:lifecycle-viewmodel-compose", Versions.lifecycle_compose)
}

fun DependencyHandlerScope.kotlinCoroutines() {
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core", Versions.Kotlin.coroutines)
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android", Versions.Kotlin.coroutines)
}

fun DependencyHandlerScope.utils() {
  api("com.jakewharton.timber:timber:4.7.1")
}

fun DependencyHandlerScope.network() {
  implementation("com.squareup.okhttp3:okhttp", Versions.okhttp)
  implementation("com.squareup.okhttp3:logging-interceptor", Versions.okhttp)
  // 🐂🍺 https://github.com/Tlaster/Hson
  implementation("com.github.Tlaster:Hson:0.1.4")
  // parse html https://github.com/jhy/jsoup/
  implementation("org.jsoup:jsoup:1.13.1")
}

fun DependencyHandlerScope.appCenter() {
  implementation("com.microsoft.appcenter:appcenter-analytics", Versions.appCenter)
  implementation("com.microsoft.appcenter:appcenter-crashes", Versions.appCenter)
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