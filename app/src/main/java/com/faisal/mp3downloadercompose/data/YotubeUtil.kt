package com.faisal.mp3downloadercompose.data

import com.chaquo.python.Python

/**
 * Created By Faisal Abdirashid on Date : 7/8/2023.
 */
class YoutubeUtil {

    var python: Python = Python.getInstance()


    fun getYoutubeVideoDetails(videoUrl : String): VideoDetails {
        val youtubeDetailsModule = python.getModule("YoutubeUtil")
        val getVideoDetails = youtubeDetailsModule.callAttr("get_video_details", videoUrl)
        val videoDetails = VideoDetails(
            "",
            0,
            "",
            0,
            0
        )
        videoDetails.title =  getVideoDetails.callAttr("get", "title").toString()
        videoDetails.duration = getVideoDetails.callAttr("get", "duration").toInt()
        videoDetails.thumbnail = getVideoDetails.callAttr("get", "thumbnail").toString()
        videoDetails.like_count = getVideoDetails.callAttr("get", "duration").toLong()
        videoDetails.view_count = getVideoDetails.callAttr("get", "duration").toLong()

        return videoDetails
    }

}



data class VideoDetails(
    var title : String,
    var duration : Int,
    var thumbnail : String,
    var like_count : Long,
    var view_count : Long
)