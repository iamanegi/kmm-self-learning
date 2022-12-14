plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.amannegi.kmmdemo.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.amannegi.kmmdemo.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:1.2.1")
    implementation("androidx.compose.ui:ui-tooling:1.2.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.2.1")
    implementation("androidx.compose.foundation:foundation:1.2.1")
    implementation("androidx.compose.material:material:1.2.1")
    implementation("androidx.activity:activity-compose:1.6.0")
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation("androidx.navigation:navigation-compose:2.5.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("com.google.accompanist:accompanist-pager:0.19.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.19.0")
}