package com.graphql.country_api.data.local.room_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.graphql.country_api.domain.model.CountryEntity

@Dao
interface CountriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countries: MutableList<CountryEntity>)
}