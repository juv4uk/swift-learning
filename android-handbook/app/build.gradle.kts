plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
}

val releaseStoreFile = System.getenv("ANDROID_KEYSTORE_PATH")
val releaseSigningConfigured = !releaseStoreFile.isNullOrBlank()
val stableDebugStoreFile = rootProject.file("ci/debug.keystore")

android {
    namespace = "ua.juv4uk.swiftpocketguide"
    compileSdk {
        version = release(37) {
            minorApiLevel = 2
        }
    }
    defaultConfig {
        applicationId = "ua.juv4uk.swiftpocketguide"
        minSdk = 26
        targetSdk = 36
        // Increase versionCode for every published Android update.
        versionCode = 11
        versionName = "1.2.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        ndk { abiFilters += "arm64-v8a" }
    }
    signingConfigs {
        create("stableDebug") {
            storeFile = stableDebugStoreFile
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
        if (releaseSigningConfigured) {
            create("release") {
                storeFile = file(releaseStoreFile!!)
                storePassword = System.getenv("ANDROID_KEYSTORE_PASSWORD")
                keyAlias = System.getenv("ANDROID_KEY_ALIAS")
                keyPassword = System.getenv("ANDROID_KEY_PASSWORD")
            }
        }
    }
    buildTypes {
        getByName("debug") {
            // Keep preview installs separate from any older app signed with an unknown key.
            applicationIdSuffix = ".preview"
            signingConfig = signingConfigs.getByName("stableDebug")
            resValue("string", "app_name", "Swift Pocket Guide Preview")
        }
        getByName("release") {
            isMinifyEnabled = false
            if (releaseSigningConfigured) {
                signingConfig = signingConfigs.getByName("release")
            }
        }
    }
    buildFeatures {
        compose = true
        resValues = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
dependencies {
    implementation(platform("androidx.compose:compose-bom:2026.09.00"))
    implementation("androidx.activity:activity-compose:1.13.0")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    testImplementation("junit:junit:4.13.2")
}
