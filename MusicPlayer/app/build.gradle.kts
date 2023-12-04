plugins {
    id("com.android.application")
}

android {
    compileSdk = 34

    defaultConfig {
        namespace = "vn.giapvantai.musicplayer"
        applicationId = "vn.giapvantai.musicplayer"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("androidx.annotation:annotation:1.7.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.core:core:1.12.0")
    implementation("androidx.fragment:fragment:1.6.2")
    implementation("androidx.media:media:1.7.0")
    implementation("androidx.palette:palette:1.0.0")
    implementation("androidx.percentlayout:percentlayout:1.0.0")
    implementation("androidx.preference:preference:1.2.1")
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    implementation("com.afollestad:material-cab:2.0.1")
    implementation("com.afollestad.material-dialogs:commons:0.9.6.0")
    implementation("com.afollestad.material-dialogs:core:0.9.6.0")
    implementation("com.github.kabouzeid:AndroidSlidingUpPanel:6")
    implementation("com.github.kabouzeid:app-theme-helper:1.3.10")
    implementation("com.github.kabouzeid:RecyclerView-FastScroll:1.0.16-kmod")
    implementation("com.github.kabouzeid:SeekArc:1.2-kmod")
    implementation("com.github.ksoichiro:android-observablescrollview:1.6.0")
    implementation("com.heinrichreimersoftware:material-intro:2.0.0")
    implementation("de.psdev.licensesdialog:licensesdialog:2.2.0")
    implementation("me.zhanghai.android.materialprogressbar:library:1.6.1")
    implementation("com.h6ah4i.android.widget.advrecyclerview:advrecyclerview:1.0.0") {
        isTransitive = true
    }

    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.github.bumptech.glide:okhttp3-integration:4.16.0")

    implementation("com.github.AdrienPoupa:jaudiotagger:2.2.3")
}