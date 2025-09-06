package com.graphql.country_api.presentation.ui.country

import android.content.Context
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.graphql.country_api.presentation.base.BaseActivity
import com.graphql.country_api.presentation.navigation.CountryNavigation
import com.graphql.country_api.worker.MyNumberWorker

class CountryActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
    }

    @Composable
    override fun Content() {
        CountryNavigation()
    }
}