plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt") // ← ¡Esta línea es clave!

}

android {
    namespace = "com.example.miappdenotas"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.miappdenotas"
        minSdk = 24
        targetSdk = 36
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
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation("androidx.recyclerview:recyclerview:1.3.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("com.google.android.material:material:1.x.x")

    // --- Room (Base de datos) ---
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")

    // You can remove this line as you are using KSP
    // annotationProcessor("androidx.room:room-compiler:$room_version") // Para Java

    // This is the correct way for Kotlin

    implementation("androidx.room:room-ktx:$room_version")
// --- Lifecycle (ViewModel y LiveData para MVVM) ---
    val lifecycle_version = "2.7.0" // Verifica la última versión

// ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
// LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
// Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    kapt("androidx.room:room-compiler:$room_version")
    // Componentes de Material Design (necesario para NavigationView y Drawer)
    implementation("com.google.android.material:material:1.x.x")

    // Si usas el antiguo soporte de compatibilidad
    implementation("androidx.drawerlayout:drawerlayout:1.x.x")

    // Dentro de dependencies { ... } en tu build.gradle.kts

// LiveData y ViewModel (Necesario para que el método 'observe' funcione)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

// Si usas Room, ya deberías tener las de corrutinas, pero verifica:
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    // Asegúrate de que tengas el resto:
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
}