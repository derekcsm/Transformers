package xyz.derekcsm.transformers

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TransformersApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        Stetho.initializeWithDefaults(this)
    }

}