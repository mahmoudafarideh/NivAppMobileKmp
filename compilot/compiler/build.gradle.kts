plugins {
    kotlin("multiplatform")
}

group = "m.a.compilot.compiler"
version = "1.0-SNAPSHOT"

kotlin {
    jvm()
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation("com.squareup:javapoet:1.13.0")
                implementation(libs.ksp)
                api(project(":compilot:common"))
            }
            kotlin.srcDir("src/main/kotlin")
            resources.srcDir("src/main/resources")
        }
    }
}