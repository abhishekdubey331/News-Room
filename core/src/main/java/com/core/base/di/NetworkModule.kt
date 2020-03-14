package com.core.base.di

import android.content.Context
import com.core.base.BuildConfig
import com.core.base.extensions.isConnected
import com.core.base.extensions.provideCache
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkModule {

    companion object {
        const val HEADER_CACHE_CONTROL = "Cache-Control"
        const val HEADER_PRAGMA = "Pragma"
    }


    @Provides
    @Singleton
    fun providesRetrofit(gsonConverterFactory: GsonConverterFactory,
                         rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
                         okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(context: Context): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(provideOfflineCacheInterceptor(context))
                .addNetworkInterceptor(provideCacheInterceptor(context))
                .cache(context.provideCache())

        if (BuildConfig.DEBUG)
            client.addNetworkInterceptor(StethoInterceptor())

        return client.build()
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun providesRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    private fun provideCacheInterceptor(context: Context): Interceptor {
        return Interceptor { chain ->
            val response: Response = chain.proceed(chain.request())
            val cacheControl: CacheControl = if (context.isConnected()) {
                CacheControl.Builder()
                        .maxAge(2, TimeUnit.HOURS)
                        .build()
            } else {
                CacheControl.Builder()
                        .maxStale(2, TimeUnit.HOURS)
                        .build()
            }
            response.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                    .build()
        }
    }

    private fun provideOfflineCacheInterceptor(context: Context): Interceptor {
        return Interceptor { chain ->
            var request: Request = chain.request()
            if (!context.isConnected()) {
                val cacheControl = CacheControl.Builder()
                        .maxStale(2, TimeUnit.HOURS)
                        .build()
                request = request.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .cacheControl(cacheControl)
                        .build()
            }
            chain.proceed(request)
        }
    }


}