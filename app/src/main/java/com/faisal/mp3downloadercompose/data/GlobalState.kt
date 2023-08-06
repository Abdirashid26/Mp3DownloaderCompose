package com.faisal.mp3downloadercompose.data

/**
 * Created By Faisal Abdirashid on Date : 7/9/2023.
 */
sealed class VideoDetailsState{
    data class Success(val videoDetails: VideoDetails) : VideoDetailsState()
    data class Failure(val errorMessage : String) : VideoDetailsState()
}
