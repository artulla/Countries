package com.graphql.country_api.presentation.ui.country

import android.os.Bundle
import androidx.compose.runtime.Composable
import com.graphql.country_api.presentation.base.BaseActivity
import com.graphql.country_api.presentation.navigation.CountryNavigation

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