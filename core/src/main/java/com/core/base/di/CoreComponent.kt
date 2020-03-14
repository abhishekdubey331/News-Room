package com.core.base.di

import android.content.Context
import android.content.SharedPreferences
import com.core.base.networking.Scheduler
import com.squareup.picasso.Picasso
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, StorageModule::class])
interface CoreComponent {

    fun context(): Context

    fun retrofit(): Retrofit

    fun sharedPreferences(): SharedPreferences

    fun scheduler(): Scheduler
}