package com.uzpsb.nearbyrestaurantsapplication.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.uzpsb.nearbyrestaurantsapplication.retrofit.AuthInterceptor
import com.uzpsb.nearbyrestaurantsapplication.retrofit.RestaurantRetrofit
import com.uzpsb.nearbyrestaurantsapplication.util.CONSTANTS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {


    @Singleton
    @Provides
    fun provideGsonBuilder():Gson{
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun providesRetrofit(gson: Gson,httpClient: OkHttpClient):Retrofit.Builder{
        return Retrofit.Builder()
            .baseUrl(CONSTANTS.BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))

    }


    @Singleton
    @Provides
    fun provideBlogService(retrofit: Retrofit.Builder):RestaurantRetrofit{
        return retrofit
            .build()
            .create(RestaurantRetrofit::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor(authInterceptor)
        .build()

}