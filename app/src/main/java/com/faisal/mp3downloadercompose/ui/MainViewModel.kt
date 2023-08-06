package com.faisal.mp3downloadercompose.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faisal.mp3downloadercompose.data.VideoDetails
import com.faisal.mp3downloadercompose.data.VideoDetailsState
import com.faisal.mp3downloadercompose.data.YoutubeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created By Faisal Abdirashid on Date : 7/9/2023.
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val youtubeUtil: YoutubeUtil
) : ViewModel() {


//    video Details Flow
    private val _videoDetails = MutableSharedFlow<VideoDetailsState>()
    val videoDetails : SharedFlow<VideoDetailsState> = _videoDetails.asSharedFlow()



     fun getVideoDetails(videoUrl : String){
        viewModelScope.launch {
            try {
                val videoDetails = youtubeUtil.getYoutubeVideoDetails(videoUrl)
                if (videoDetails.title.isNotEmpty()){
                    _videoDetails.emit(VideoDetailsState.Success(videoDetails))
                }
            }catch (t : Throwable){
                _videoDetails.emit(VideoDetailsState.Failure(t.message.toString()))
            }
        }
    }


}