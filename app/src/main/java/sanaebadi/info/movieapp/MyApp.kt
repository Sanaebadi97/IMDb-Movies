package sanaebadi.info.movieapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import sanaebadi.info.movieapp.di.viewModelModule

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApp)
            modules(viewModelModule)
        }
    }
}