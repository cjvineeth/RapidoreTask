package com.app.rapidore.di

import android.content.Context
import com.app.rapidore.common.Constants
import com.app.rapidore.data.db.ProductDatabase
import com.app.rapidore.data.remote.ProductsApi
import com.app.rapidore.data.repository.ProductRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideProductsApi(okHttpClient: OkHttpClient): ProductsApi {

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ProductsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideClient():OkHttpClient{
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Set your desired log level here
        }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        return okHttpClient
    }


    @Provides
    @Singleton
    fun providePlanetaryRepository(api: ProductsApi, db: ProductDatabase): ProductRepoImpl {
        return ProductRepoImpl(api, db)
    }

    @Provides
    fun provideDB(@ApplicationContext appContext: Context): ProductDatabase {
        return ProductDatabase.getInstance(appContext)
    }

}