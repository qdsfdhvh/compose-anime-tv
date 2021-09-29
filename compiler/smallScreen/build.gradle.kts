plugins {
  kotlin("jvm")
}

dependencies {
  implementation("com.squareup:kotlinpoet:1.9.0")
  implementation("com.google.devtools.ksp:symbol-processing-api:${Versions.ksp}")
}
