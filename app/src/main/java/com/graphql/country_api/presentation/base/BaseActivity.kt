package com.graphql.country_api.presentation.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.graphql.country_api.presentation.base.theme.CountryTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * BaseActivity for Compose screens.
 * Handles setContent and provides a single point for common setup.
 */
@AndroidEntryPoint
abstract class BaseActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CountryTheme { Content() }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * Composable content for the activity.
     * Subclasses must implement this.
     */
    @Composable
    abstract fun Content()
}