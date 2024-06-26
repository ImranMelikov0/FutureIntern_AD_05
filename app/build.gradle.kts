
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "com.imranmelikov.qrcodescankotlin"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.imranmelikov.qrcodescankotlin"
        minSdk = 27
        targetSdk = 34
        versionCode = 2
        versionName = "1.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding =true
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation("com.google.zxing:core:3.5.2")

    implementation("com.google.mlkit:barcode-scanning:17.0.2")

    // CameraX core library using the camera2 implementation
   val camerax_version = "1.2.0-beta02"
    implementation( "androidx.camera:camera-camera2:${camerax_version}")
    // If you want to additionally use the CameraX Lifecycle library
    implementation ("androidx.camera:camera-lifecycle:${camerax_version}")
    // If you want to additionally use the CameraX View class
    implementation ("androidx.camera:camera-view:${camerax_version}")

    /**
     * ZXing 3.4+ uses Java 8 language features, so it is required to enable core library desugaring
     * in order to use the Zxing on API < 23
     */
    coreLibraryDesugaring ("com.android.tools:desugar_jdk_libs:1.1.5")

}