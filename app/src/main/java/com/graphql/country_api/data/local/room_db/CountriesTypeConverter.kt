package com.graphql.country_api.data.local.room_db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.graphql.country_api.domain.model.Language
import com.graphql.country_api.domain.model.States

class CountriesTypeConverter {

    @TypeConverter
    fun fromCurrencyList(list: MutableList<String>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun toCurrencyList(json: String?): MutableList<String>? {
        if (json.isNullOrEmpty()) return mutableListOf()
        val gson = Gson()
        val type = object : TypeToken<MutableList<String>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromLanguages(language: MutableList<Language>): String {
        val gson = Gson()
        val type = object : TypeToken<MutableList<Language>>() {}.type
        return gson.toJson(language, type)
    }

    @TypeConverter
    fun toLanguages(value: String): MutableList<Language> {
        val gson = Gson()
        val type = object : TypeToken<MutableList<Language>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromStates(states: MutableList<States>): String {
        val gson = Gson()
        val type = object : TypeToken<MutableList<States>>() {}.type
        return gson.toJson(states, type)
    }

    @TypeConverter
    fun toStates(value: String): MutableList<States> {
        val gson = Gson()
        val type = object : TypeToken<MutableList<States>>() {}.type
        return gson.fromJson(value, type)
    }
}