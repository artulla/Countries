package com.graphql.country_api.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.graphql.country_api.data.local.room_db.CountriesTypeConverter

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    var continentName: String? = null,
    var continentCode: String? = null,
    var capital: String? = null,
    var code: String? = null,
    var phone: String? = null,
    var emoji: String? = null,
    var name: String? = null,
    @TypeConverters(CountriesTypeConverter::class) var currencies: MutableList<String>? = null,
    @TypeConverters(CountriesTypeConverter::class) var languages: MutableList<Language>? = null,
    @TypeConverters(CountriesTypeConverter::class) var states: MutableList<States>? = null,
)