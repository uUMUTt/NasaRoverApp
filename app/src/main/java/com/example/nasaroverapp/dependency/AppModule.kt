package com.example.nasaroverapp.dependency

import com.example.nasaroverapp.service.IRetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    private val baseUrl = "https://api.nasa.gov/mars-photos/api/"

    @Singleton
    @Provides
    fun getRetrofitServiceInstance(retrofit: Retrofit): IRetrofitService {
        return retrofit.create(IRetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}