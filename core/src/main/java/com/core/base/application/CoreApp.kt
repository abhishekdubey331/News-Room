package com.core.base.application

import android.app.Application
import com.facebook.stetho.Stetho
import com.core.base.BuildConfig
import com.core.base.di.AppModule
import com.core.base.di.CoreComponent
import com.core.base.di.DaggerCoreComponent
import com.orhanobut.hawk.Hawk

open class CoreApp : Application() {

    companion object {
        lateinit var coreComponent: CoreComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDI()
        initStetho()
        initHawkForCache()
    }

    private fun initHawkForCache() {
        Hawk.init(this).build()
    }

    private fun initStetho() {
        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this)
    }

    private fun initDI() {
        coreComponent = DaggerCoreComponent.builder().appModule(AppModule(this)).build()
    }


}