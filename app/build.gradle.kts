plugins { id("com.android.application") }

android {
    namespace = "pl.szymon.tvpanel"
    compileSdk = 34
    defaultConfig {
        applicationId = "pl.szymon.tvpanel"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes { release { isMinifyEnabled = false; signingConfig = signingConfigs.getByName("debug") } }
    compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }
}
