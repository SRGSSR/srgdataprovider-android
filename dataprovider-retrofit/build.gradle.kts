plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    `maven-publish`
}

android {
    namespace = "ch.srg.dataProvider.integrationlayer"
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        group = Config.maven_group
        version = Config.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        resValues = false
        shaders = false
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
        abortOnError = false
        sarifReport = true
        sarifOutput = rootProject.layout.buildDirectory.file("reports/android-lint/${project.name}.sarif").get().asFile
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    api(project(":data"))
    api(libs.retrofit)
    compileOnly(libs.androidx.annotation)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.logging.interceptor)

    testImplementation(libs.junit)
    testRuntimeOnly(libs.robolectric)
    testImplementation(libs.ext.junit)
}

publishing {
    publications {
        register<MavenPublication>("gpr") {
            afterEvaluate {
                from(components["release"])
            }
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/SRGSSR/srgdataprovider-android")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
        maven {
            url = uri("https://maven.ecetest.rts.ch/content/repositories/srg-letterbox-releases/")
            credentials {
                username = project.findProperty("sonatypeUsername") as String? ?: System.getenv("SONATYPE_USERNAME")
                password = project.findProperty("sonatypePassword") as String? ?: System.getenv("SONATYPE_PASSWORD")
            }
        }
    }
}
