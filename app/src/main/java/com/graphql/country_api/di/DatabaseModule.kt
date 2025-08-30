package com.graphql.country_api.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase.JournalMode
import com.graphql.country_api.BuildConfig
import com.graphql.country_api.data.local.room_db.AppDatabase
import com.graphql.country_api.data.local.room_db.CountriesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext app: Context
    ): AppDatabase {
        return databaseBuilder(
            app, AppDatabase::class.java, BuildConfig.DATABASE_NAME
        ).setJournalMode(JournalMode.TRUNCATE).build()
    }

    @Provides
    fun provideCountriesDao(appDatabase: AppDatabase): CountriesDao = appDatabase.countriesDao()
}