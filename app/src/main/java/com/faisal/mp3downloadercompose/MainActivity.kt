package com.faisal.mp3downloadercompose

import android.annotation.SuppressLint
import android.graphics.Paint.Align
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.faisal.mp3downloadercompose.ui.common.RequestPermission
import com.faisal.mp3downloadercompose.ui.screens.InitialScreen
import com.faisal.mp3downloadercompose.ui.theme.Mp3DownloaderComposeTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Mp3DownloaderComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Mp3YoutubeDownloaderApp()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Mp3YoutubeDownloaderApp() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(modifier = Modifier.fillMaxWidth())
        }
    ) {
        val padding = it.calculateTopPadding()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = padding) // Apply content padding
        ) {
            // Your content goes here
            InitialScreen()
        }
    }

    RequestPermission(
        permission = android.Manifest.permission.CAMERA,
        permissionNotGranted = {
                     Log.i("Permission","Not Granted")
        },
        permissionNotAvailable = {
            Log.i("Permission","Not Available")
        }) {
        Log.i("Permission","Permission Given")
    }
}


@Composable
fun TopAppBar(
    modifier: Modifier = Modifier
) {
    
    Row(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 10.dp)
    ){
        
        Image(
            modifier = Modifier
                .width(204.dp)
                .height(32.dp)
                .padding(end = 10.dp)
                ,
            painter = painterResource(id = R.drawable.top_bar_logo),
            contentDescription = "Youtube Logo"
        )
        
    }
    
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Mp3DownloaderComposeTheme {
        Mp3YoutubeDownloaderApp()
    }
}