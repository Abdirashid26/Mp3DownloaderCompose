package com.faisal.mp3downloadercompose.di

import com.faisal.mp3downloadercompose.data.YoutubeUtil
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created By Faisal Abdirashid on Date : 7/9/2023.
 */

@Module
object Mp3ComposeModule {

    @Singleton
    @Provides
    fun providesYoutubeUtil() : YoutubeUtil{
        return YoutubeUtil()
    }

}