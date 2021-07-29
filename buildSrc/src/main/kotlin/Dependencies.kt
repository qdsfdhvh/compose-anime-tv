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

fun DependencyHandlerScope.compose() {
  implementation("androidx.compose.ui:ui", Versions.compose)
  implementation("androidx.compose.ui:ui-tooling", Versions.compose)
  androidTestImplementation("androidx.compose.ui:ui-test", Versions.compose)
  implementation("androidx.compose.foundation:foundation", Versions.compose)
  implementation("androidx.compose.animation:animation", Versions.compose)
  implementation("androidx.compose.material:material", Versions.compose)
  implementation("androidx.compose.material:material-icons-core", Versions.compose)
  implementation("androidx.compose.material:material-icons-extended", Versions.compose)
  // implementation("androidx.compose.runtime:runtime-livedata", Versions.compose)
}

fun DependencyHandlerScope.activity() {
  implementation("androidx.activity:activity-ktx", Versions.activity)
  implementation("androidx.activity:activity-compose", Versions.activity)
}

fun DependencyHandlerScope.hilt() {
  implementation("com.google.dagger:hilt-android", Versions.hilt)
  kapt("com.google.dagger:hilt-android-compiler", Versions.hilt)
  implementation("androidx.hilt:hilt-work", Versions.androidx_hilt)
  kapt("androidx.hilt:hilt-compiler", Versions.androidx_hilt)
  implementation("androidx.hilt:hilt-navigation-compose:1.0.0-alpha03")
}

fun DependencyHandlerScope.lifecycle() {
  implementation("androidx.lifecycle:lifecycle-runtime-ktx", Versions.lifecycle)
  implementation("androidx.lifecycle:lifecycle-viewmodel-ktx", Versions.lifecycle)
  implementation("androidx.lifecycle:lifecycle-common-java8", Versions.lifecycle)
  implementation("androidx.lifecycle:lifecycle-viewmodel-compose", Versions.lifecycle_compose)
}

fun DependencyHandlerScope.kotlinCoroutines() {
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core", Versions.Kotlin.coroutines)
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test", Versions.Kotlin.coroutines)
}

fun DependencyHandlerScope.accompanist() {
  implementation("com.google.accompanist:accompanist-coil", Versions.accompanist)
  implementation("com.google.accompanist:accompanist-insets", Versions.accompanist)
}

fun DependencyHandlerScope.navigation() {
  implementation("androidx.navigation:navigation-compose", Versions.navigation)
}

fun DependencyHandlerScope.junit5() {
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}

fun DependencyHandlerScope.junit4() {
  testImplementation("junit:junit:4.13.2")
}

fun DependencyHandlerScope.mockito() {
  testImplementation("org.mockito:mockito-core:3.11.2")
  testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
}

fun DependencyHandlerScope.androidTest() {
  testImplementation("androidx.arch.core:core-testing:2.1.0")
  androidTestImplementation("androidx.arch.core:core-testing:2.1.0")
  androidTestImplementation("androidx.test:core", Versions.androidx_test)
  androidTestImplementation("androidx.test:runner", Versions.androidx_test)
  androidTestImplementation("androidx.test.ext:junit", Versions.extJUnitVersion)
  androidTestImplementation("androidx.test.espresso:espresso-core", Versions.espressoVersion)
}