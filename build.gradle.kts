plugins {
  kotlin("jvm") version "1.5.10"
  kotlin("plugin.serialization") version "1.5.10"
  kotlin("kapt") version "1.5.10"
  idea
}

idea {
  module {
    isDownloadSources = true
  }
}

group = "me.yujinyan"
version = "1.0-SNAPSHOT"

val ktorVersion = "1.5.2"
val arrowVersion = "0.11.0"
val moshiVersion = "1.12.0"


repositories {
//  maven("https://maven.aliyun.com/repository/public/") {
//    content { excludeGroup("org.jetbrains.bio") }
//  }
//  maven("https://maven.aliyun.com/repository/spring/")
//  maven("https://maven.aliyun.com/repository/jcenter")
  maven("https://jitpack.io")
  jcenter()
  maven("https://kotlin.bintray.com/kotlinx")
  mavenCentral()
  google()
}

dependencies {
  implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.1-1.4.0-rc")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
  implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3")
  implementation("com.github.ajalt.clikt:clikt:3.0.1")
  implementation("io.ktor:ktor-server-core:$ktorVersion")
  implementation("io.ktor:ktor-html-builder:$ktorVersion")
  implementation("io.ktor:ktor-server-netty:$ktorVersion")
  implementation("io.ktor:ktor-client-cio:$ktorVersion")
  implementation("io.arrow-kt:arrow-core:$arrowVersion")
  implementation("io.arrow-kt:arrow-syntax:$arrowVersion")
  implementation("org.ktorm:ktorm-core:3.4.1")
  implementation("org.ktorm:ktorm-support-mysql:3.4.1")
  implementation("org.ktorm:ktorm-support-postgresql:3.4.1")
  implementation("org.litote.kmongo:kmongo:4.0.3")
  implementation("mysql:mysql-connector-java:8.0.21")
  runtimeOnly("org.postgresql:postgresql:42.2.20")
//  implementation("me.liuwj.ktorm:ktorm-support-mysql:3.0.0")
  implementation("io.reactivex.rxjava3:rxjava:3.0.6")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")
  implementation("com.squareup.moshi:moshi:$moshiVersion")
  implementation("com.squareup.retrofit2:retrofit:2.9.0")
  implementation("com.github.breandan:kotlingrad:0.4.0")
  implementation("com.github.breandan:kaliningraph:0.1.4")
  implementation("io.lettuce:lettuce-core:6.0.1.RELEASE")
  implementation("com.graphql-java:java-dataloader:2.2.3")
  implementation("com.github.yujinyan:faktory:ba7189282a")
  implementation("androidx.datastore:datastore-preferences-core:1.0.0-alpha07")
  kapt("com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion")
//  annotationProcessor("io.arrow-kt:arrow-meta:$arrowVersion")
  implementation(kotlin("script-runtime"))
}
