package com.graphql.country_api

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class CountryIntegrationTest {

    @Test
    fun fetchCountriesFromApi() = runBlocking {
        val repo = CountryRepository()
        val countries = repo.fetchCountries()

        assertTrue("Countries list should not be empty", countries.isNotEmpty())
    }
}
