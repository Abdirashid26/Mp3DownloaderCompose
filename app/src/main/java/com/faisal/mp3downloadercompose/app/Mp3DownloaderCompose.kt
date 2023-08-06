package com.faisal.mp3downloadercompose.app

import android.app.Application
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import dagger.hilt.android.HiltAndroidApp

/**
 * Created By Faisal Abdirashid on Date : 7/9/2023.
 */

@HiltAndroidApp
class Mp3DownloaderCompose : Application() {

    override fun onCreate() {
        super.onCreate()
        if( !Python.isStarted() ) {
            Python.start( AndroidPlatform( this ) )
        }
    }
}