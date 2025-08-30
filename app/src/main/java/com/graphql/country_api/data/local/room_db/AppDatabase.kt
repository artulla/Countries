package com.graphql.country_api.data.local.room_db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.graphql.country_api.BuildConfig
import com.graphql.country_api.domain.model.CountryEntity

/**
 * Main Room database for the application.
 *
 * Contains all the entities and their corresponding DAOs.
 */
@Database(
    version = BuildConfig.DATABASE_VERSION, // Database version from constants
    exportSchema = false,                   // Disable schema export for simplicity
    entities = [CountryEntity::class]       // List all your entities here
)
@TypeConverters(CountriesTypeConverter::class) // Add your TypeConverters if needed, e.g., DateConverter, ListConverter
abstract class AppDatabase : RoomDatabase() {

    // TODO: Define DAOs here
     abstract fun countriesDao(): CountriesDao
}
