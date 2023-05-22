plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "ch.srg.dataProvider.integrationlayer"
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    api(project(mapOf("path" to ":data")))
    implementation("androidx.core:core-ktx:${Versions.coreKtx}")
    api("androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle_version}")

    implementation("com.google.dagger:dagger:${Versions.dagger}")
    kapt("com.google.dagger:dagger-compiler:${Versions.dagger}")

    //retrofit implementation
    api("com.squareup.retrofit2:retrofit:${Versions.retrofit_version}")
    api("com.squareup.retrofit2:converter-gson:${Versions.retrofit_version}")
    //noinspection GradleDependency
    implementation("com.squareup.okhttp3:logging-interceptor:${Versions.okHttp_version}")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}