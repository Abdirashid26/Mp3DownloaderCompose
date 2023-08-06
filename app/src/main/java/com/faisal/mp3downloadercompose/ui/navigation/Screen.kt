package com.faisal.mp3downloadercompose.ui.navigation

/**
 * Created By Faisal Abdirashid on Date : 7/8/2023.
 */
sealed class Screen(val route : String){
    object InitialScreen : Screen("initial_screen")
    object DownloadScreen : Screen("download_screen")
}
