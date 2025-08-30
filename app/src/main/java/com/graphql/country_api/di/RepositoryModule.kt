package com.graphql.country_api.di

import com.graphql.country_api.data.repository_impl.CountryRepositoryImpl
import com.graphql.country_api.domain.repository.CountryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module to provide repository implementations for dependency injection.
 *
 * Binds the concrete [CountryRepositoryImpl] to the [CountryRepository] interface
 * so that Hilt can inject it wherever [CountryRepository] is required.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * Bind the [CountryRepositoryImpl] implementation to the [CountryRepository] interface.
     *
     * @param impl The concrete repository implementation.
     * @return The [CountryRepository] interface type for injection.
     */
    @Binds
    @Singleton
    abstract fun bindCountryRepository(
        impl: CountryRepositoryImpl
    ): CountryRepository
}
