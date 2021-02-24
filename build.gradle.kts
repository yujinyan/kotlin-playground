import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.4.21"
  kotlin("plugin.serialization") version "1.4.21"
  kotlin("kapt") version "1.4.21"
  idea
}

idea {
  module {
    isDownloadSources = true
  }
}

group = "me.yujinyan"
version = "1.0-SNAPSHOT"
val ktorVersion = "1.4.0"
val arrowVersion = "0.11.0"

tasks.withType<KotlinCompile> {
  kotlinOptions {
    jvmTarget = "1.8"
  }
}

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
  maven {
    url = uri("https://code.jojotoo.com/api/v4/projects/33/packages/maven")
    @Suppress("SpellCheckingInspection")
    credentials(HttpHeaderCredentials::class) {
      name = "Deploy-Token"
      value = "4uLszAaomQswBx_SKSPz"
    }
    authentication {
      create<HttpHeaderAuthentication>("header")
    }
  }
}

dependencies {
  implementation(kotlin("stdlib"))
  implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.1-1.4.0-rc")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.4.2")
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
  implementation("io.reactivex.rxjava3:rxjava:3.0.6")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0-RC2")
  implementation("com.squareup.moshi:moshi:1.9.3")
  implementation("com.squareup.retrofit2:retrofit:2.9.0")
  implementation("com.github.breandan:kotlingrad:0.4.0")
  implementation("com.github.breandan:kaliningraph:0.1.4")
  implementation("io.lettuce:lettuce-core:6.0.1.RELEASE")
  implementation("com.graphql-java:java-dataloader:2.2.3")
  implementation("com.github.yujinyan:faktory:ba7189282a")
  kapt("com.squareup.moshi:moshi-kotlin-codegen:1.9.3")
//  annotationProcessor("io.arrow-kt:arrow-meta:$arrowVersion")
  implementation(kotlin("script-runtime"))
}
