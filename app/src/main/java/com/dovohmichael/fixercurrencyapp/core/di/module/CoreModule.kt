package com.dovohmichael.fixercurrencyapp.core.di.module

import android.content.Context
import com.dovohmichael.fixercurrencyapp.BuildConfig
import com.dovohmichael.fixercurrencyapp.core.Constants
import com.dovohmichael.fixercurrencyapp.core.data.db.CurrencyDatabase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class CoreModule {

    companion object {


        @Singleton
        @Provides
        fun provideContext(@ApplicationContext context: Context): Context {
            return context
        }


        @Singleton
        @Provides
        fun provideMoshi(): Moshi {
            return Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        }

        @Singleton
        @Provides
        fun provideOkHttpLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor()
                .apply {
                    level =
                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                        else HttpLoggingInterceptor.Level.NONE
                }
        }

        private val requestInterceptor by lazy {
            Interceptor { chain ->

                val request = chain.request()
                    .newBuilder().addHeader("apikey",BuildConfig.API_KEY)
                    .build()

                return@Interceptor chain.proceed(request)
            }
        }

        @Singleton
        @Provides
        fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
            return OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor).addInterceptor(requestInterceptor)
                .build()
        }

        @Singleton
        @Provides
        fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
        }


        @Singleton
        @Provides
        fun provideCurrencyDatabase(context: Context): CurrencyDatabase {
            return CurrencyDatabase.getInstance(context)
        }

    }

}