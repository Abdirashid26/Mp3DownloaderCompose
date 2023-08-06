package com.faisal.mp3downloadercompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

/**
 * Created By Faisal Abdirashid on Date : 7/8/2023.
 */


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.InitialScreen.route){
        composable(route = Screen.InitialScreen.route){

        }
    }
}