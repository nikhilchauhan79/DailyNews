plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  id("kotlin-kapt")
  id("com.google.dagger.hilt.android")
}

android {
  namespace = "com.example.dailynews"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.example.dailynews"
    minSdk = 24
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.1"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
  // Allow references to generated code
  kapt {
    correctErrorTypes = true
  }
}

dependencies {
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)
  implementation(libs.play.services.wearable)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)
  implementation(libs.coil.compose)

  implementation(libs.hilt.android)
  kapt(libs.hilt.android.compiler)

  implementation(libs.androidx.room.runtime)
  kapt(libs.androidx.room.compiler)
  implementation(libs.androidx.room.ktx)

  implementation (libs.gson)
  implementation (libs.retrofit)
  implementation (libs.converter.gson)
  implementation(libs.logging.interceptor)

  // ViewModel
  implementation(libs.androidx.lifecycle.viewmodel.ktx)
  // ViewModel utilities for Compose
  implementation(libs.androidx.lifecycle.viewmodel.compose)
  // LiveData
  implementation(libs.androidx.lifecycle.livedata.ktx)
  // Lifecycle utilities for Compose
  implementation(libs.androidx.lifecycle.runtime.compose)

  // Saved state module for ViewModel
  implementation(libs.androidx.lifecycle.viewmodel.savedstate)

  // Annotation processor
  kapt(libs.androidx.lifecycle.compiler)

  // optional - helpers for implementing LifecycleOwner in a Service
  implementation(libs.androidx.lifecycle.service)

  // optional - ProcessLifecycleOwner provides a lifecycle for the whole application process
  implementation(libs.androidx.lifecycle.process)

  implementation(libs.lifecycle.livedata.ktx)

  // Jetpack Compose integration
  implementation(libs.androidx.navigation.compose)

  // Views/Fragments integration
  implementation(libs.androidx.navigation.fragment.ktx)
  implementation(libs.androidx.navigation.ui)

  // Feature module support for Fragments
  implementation(libs.androidx.navigation.dynamic.features.fragment)

  // Testing Navigation
  androidTestImplementation(libs.androidx.navigation.testing)
}