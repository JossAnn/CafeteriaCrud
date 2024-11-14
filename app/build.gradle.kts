plugins {
    alias(libs.plugins.android.application)
    // alias(libs.plugins.kotlin.android) // Descomentar si usas Kotlin
}

android {
    namespace = "com.example.adaptadores"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.adaptadores"
        minSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("androidx.media3:media3-exoplayer:1.4.1")
    implementation(libs.androidx.media3.exoplayer.dash)
    implementation(libs.media3.ui)
    implementation (libs.exoplayer)
    implementation(libs.support.annotations)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

