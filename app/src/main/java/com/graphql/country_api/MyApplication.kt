package com.graphql.country_api

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Main Application class annotated with [@HiltAndroidApp] to enable Hilt dependency injection.
 */
@HiltAndroidApp
class MyApplication : Application() {

    companion object {
        /** Singleton instance of the Application for global access. */
        private lateinit var instance: MyApplication

        /** Retrieve the singleton instance of [MyApplication]. */
        fun getInstance(): MyApplication = instance
    }

    override fun onCreate() {
        super.onCreate()
        // Initialize the singleton instance
        instance = this
    }
}
