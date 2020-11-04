import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.4.10"
  kotlin("plugin.serialization") version "1.4.10"
  kotlin("kapt") version "1.4.10"
}

group = "me.yujinyan"
version = "1.0-SNAPSHOT"
val ktorVersion = "1.4.0"
val arrowVersion = "0.10.4"

tasks.withType<KotlinCompile> {
  kotlinOptions {
    jvmTarget = "1.8"
  }
}

repositories {
  maven("https://maven.aliyun.com/repository/public/")
  maven("https://maven.aliyun.com/repository/spring/")
  maven("https://maven.aliyun.com/repository/jcenter")
  maven("https://jitpack.io")
  maven("https://kotlin.bintray.com/kotlinx")
  mavenCentral()
  jcenter()
}

dependencies {
  implementation(kotlin("stdlib"))
  implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.1-1.4.0-rc")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.0")
  implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3")
  implementation("com.github.ajalt.clikt:clikt:3.0.1")
  implementation("io.ktor:ktor-server-core:$ktorVersion")
  implementation("io.ktor:ktor-html-builder:$ktorVersion")
  implementation("io.ktor:ktor-server-netty:$ktorVersion")
  implementation("io.ktor:ktor-client-cio:$ktorVersion")
  implementation("io.arrow-kt:arrow-core:$arrowVersion")
  implementation("io.arrow-kt:arrow-syntax:$arrowVersion")
  implementation("me.liuwj.ktorm:ktorm-core:3.0.0")
  implementation("org.litote.kmongo:kmongo:4.0.3")
  implementation("mysql:mysql-connector-java:8.0.21")
  implementation("me.liuwj.ktorm:ktorm-support-mysql:3.0.0")
  implementation ("io.reactivex.rxjava3:rxjava:3.0.6")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0-RC2")
  implementation("com.squareup.moshi:moshi:1.9.3")

  kapt("com.squareup.moshi:moshi-kotlin-codegen:1.9.3")
//  annotationProcessor("io.arrow-kt:arrow-meta:$arrowVersion")
}
