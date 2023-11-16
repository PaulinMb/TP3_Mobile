plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.partiemysql"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.partiemysql"
        minSdk = 19
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("com.android.support:support-annotations:27.1.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("com.android.support.test:runner:1.0.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.loopj.android:android-async-http:1.4.9")
    implementation ("com.squareup.picasso:picasso:2.5.2")
}