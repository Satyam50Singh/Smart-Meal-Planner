plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.satya.smartmealplanner"
    compileSdk = 35

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.satya.smartmealplanner"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

         buildConfigField("String", "SPOONACULAR_API_KEY", "\"5e5f5b7f9b464322a395f3a15ff4466f\"")
//        buildConfigField("String", "SPOONACULAR_API_KEY", "\"be3c1dd814cd47ca802c660d58135936\"")

    }

    buildTypes {
        signingConfigs {
            create("release") {
                storeFile =
                    file("/Users/satyamsingh/Documents/Workspace/Android/SmartMealPlanner/app/src/main/res/raw/release_key.jks")
                storePassword = "Satya@2025"
                keyAlias = "Satya@2025"
                keyPassword = "Satya@2025"
            }
        }
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    ksp {
        arg("dagger.hilt.disableModulesHaveInstallInCheck", "true")
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.gsonConverter)
    implementation(libs.okhttp)
    implementation(libs.loggingInterceptor)

    // Hilt Core
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Hilt for Jetpack Compose (optional but recommended)
    implementation(libs.androidx.hilt.navigation.compose)

    // ViewModel injection
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Compose runtime
    implementation(libs.androidx.compose.runtime)

    // Coil
    implementation(libs.coil.compose)

    // Jetpack Compose
    implementation(libs.androidx.navigation.compose)

    // Room DB
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    // Swipe to refresh
    implementation(libs.swipe.to.refresh)

    // Compose Charts
    implementation(libs.compose.charts)

    // firebase
    implementation(platform(libs.firebase.bom))

    // Firestore (for favorite recipes)
    implementation(libs.firebase.firestore)

    // Crashlytics
    implementation(libs.firebase.crashlytics)

}