plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.happy_wallet_mobile"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.happy_wallet_mobile"
        minSdk = 26
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    // Nếu bạn đang dùng Kotlin, thêm phần này
    // kotlinOptions {
    //     jvmTarget = '1.8'
    // }
}

dependencies {

    implementation("com.google.android.material:material:1.10.0")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation ("commons-io:commons-io:2.11.0")

    // CircleImageView (for circular avatars) - BỔ SUNG DÒNG NÀY
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    // Chỉ giữ một phiên bản Glide
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.1")

    implementation ("androidx.lifecycle:lifecycle-viewmodel:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-livedata:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-common-java8:2.6.2" )
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation ("com.google.android.flexbox:flexbox:3.0.0")

    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")

}