package com.graphql.country_api.di

import com.apollographql.apollo.ApolloClient
import com.graphql.country_api.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    *//*@Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY) // Set log level

        val gsonBuilder = GsonBuilder().setLenient()
        val okHttpClient: OkHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .readTimeout(100, TimeUnit.SECONDS).connectTimeout(100, TimeUnit.SECONDS).build()

        return Retrofit.Builder().baseUrl(ApiConstants.BASE_URL_NEWS)
            .client(okHttpClient)// Set the OkHttpClient with the logging interceptor
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create())).build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }*//*
}*/

/**
 * Hilt module to provide network-related dependencies for the app.
 * Currently provides a singleton ApolloClient for GraphQL API communication.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provides a singleton [ApolloClient] instance for making GraphQL requests.
     *
     * @return Configured [ApolloClient] instance.
     */
    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {/*   val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY) // Set log level

        val okHttpClient: OkHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .readTimeout(100, TimeUnit.SECONDS).connectTimeout(100, TimeUnit.SECONDS).build()*/

        return ApolloClient.Builder().serverUrl(BuildConfig.BASE_URL) // Country GraphQL endpoint
//            .okHttpClient(okHttpClient)
            // Optional: Add interceptors, caching, logging, etc.
            // .addHttpInterceptor(...)
            // .webSocketServerUrl(...)
            .build()
    }
}