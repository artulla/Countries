plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.apollo.graphql)
    alias(libs.plugins.kotlin.compose)
}

apollo {
    service("service") {
        packageName.set("com.graphql.country_api")
        schemaFile.set(file("src/main/graphql/com/graphql/country_api/schema.graphqls"))
        introspection {
            endpointUrl.set("https://countries.trevorblades.com/")
        }
    }
}

android {
    namespace = "com.graphql.country_api"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.graphql.country_api"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
        create("country_api") {
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://countries.trevorblades.com/\"")
            buildConfigField("String", "DATABASE_NAME", "\"countries.db\"")
            buildConfigField("int", "DATABASE_VERSION", "1")
            signingConfig = signingConfigs.getByName("debug")
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
        viewBinding = true
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.room.common.jvm)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.apollo.graphql.runtime)
//    implementation(libs.okhttp3)
//    implementation(libs.logging.interceptor)
    implementation(libs.dagger)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    ksp(libs.dagger.compiler)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    ksp(libs.hilt)
    implementation(libs.lifecycle.extensions)
    implementation(libs.worker)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.hilt.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    androidTestImplementation(libs.room.testing)
    implementation(libs.paging)
    implementation(libs.paging.compose)
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.converter.gson)
    implementation(libs.accompanist)
}