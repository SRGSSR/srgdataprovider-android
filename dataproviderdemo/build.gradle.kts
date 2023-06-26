plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "ch.srgssr.dataprovider.demo"
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = "ch.srgssr.dataprovider.demo"
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = 1
        versionName = "4.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isDebuggable = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    lint {
        // https://developer.android.com/reference/tools/gradle-api/4.1/com/android/build/api/dsl/LintOptions
        abortOnError = true
        checkAllWarnings = true
        checkDependencies = false
        xmlReport = true // Enable for Danger Android Lint
        xmlOutput = file("${project.rootDir}/build/reports/android-lint.xml")
        disable.add("LogConditional")
    }
}

dependencies {
    implementation(project(mapOf("path" to ":dataprovider-retrofit")))
    implementation(project(mapOf("path" to ":dataprovider-paging")))
    implementation("androidx.core:core-ktx:${Versions.coreKtx}")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
