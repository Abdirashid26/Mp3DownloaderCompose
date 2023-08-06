package com.faisal.mp3downloadercompose.ui.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chaquo.python.Python
import com.faisal.mp3downloadercompose.R
import com.faisal.mp3downloadercompose.data.VideoDetails
import com.faisal.mp3downloadercompose.data.YoutubeUtil
import com.faisal.mp3downloadercompose.ui.MainViewModel
import com.faisal.mp3downloadercompose.ui.common.FolderPicker
import com.faisal.mp3downloadercompose.ui.common.MyTextView
import com.faisal.mp3downloadercompose.ui.theme.Mp3DownloaderComposeTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch


/**
 * Created By Faisal Abdirashid on Date : 7/8/2023.
 */
@Composable
fun InitialScreen(
    modifier : Modifier = Modifier,
//    mainViewModel: MainViewModel by hiltViewModel
) {
    Column(
        modifier = modifier
    ){

        val localContext = LocalContext.current

        val scope = rememberCoroutineScope()


        var youtubeLink by rememberSaveable {
            mutableStateOf("")
        }

        var folderDestination by rememberSaveable {
            mutableStateOf("")
        }

        MyTextView(textValue = "Youtube Link")
        Spacer(modifier = Modifier
            .height(8.dp)
            .fillMaxWidth())
        YoutubeLinkEditTextView(youtubeLink = youtubeLink, onClick = {
            youtubeLink = it
        }){
            youtubeLink = ""
        }
        Spacer(modifier = Modifier
            .height(10.dp)
            .fillMaxWidth())
        MyTextView(textValue = "Folder Destination")
        Spacer(modifier = Modifier
            .height(8.dp)
            .fillMaxWidth())
        FolderDestinationEditTextView(folderDestination = folderDestination){
            folderDestination = it
        }
        Spacer(modifier = Modifier
            .height(8.dp)
            .fillMaxWidth())
        InfoComposable()
        Spacer(modifier = Modifier
            .height(16.dp)
            .fillMaxWidth())
        MainButton(textValue = "Continue"){
            mToast(localContext)
            scope.launch{
                   val videoDetails =  getYoutubeVideo(youtubeLink)
            }
        }
    }
}


private fun getYoutubeVideo(videoUrl : String): VideoDetails {
    return YoutubeUtil.getYoutubeVideoDetails(videoUrl)
}

private fun mToast(context: Context){
    Toast.makeText(context, "Continue Button Pressed", Toast.LENGTH_SHORT).show()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YoutubeLinkEditTextView(
    modifier: Modifier = Modifier,
    youtubeLink : String,
    onClick: (newValue : String) -> Unit,
    clearButton : () -> Unit
) {



        OutlinedTextField(
            value = youtubeLink,
            onValueChange = { newValue: String ->
                onClick(newValue)
            },
            label = { Text("YouTube Link") },
            textStyle = MaterialTheme.typography.labelSmall,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Link,
                    contentDescription = "Leading Icon",
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            trailingIcon = {
                if (youtubeLink.isNotEmpty()){
                    IconButton(onClick = clearButton) {
                        Icon(imageVector = Icons.Default.Close, contentDescription ="Clear TextField")
                    }
                }
            }
        )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FolderDestinationEditTextView(
    modifier: Modifier = Modifier,
    folderDestination : String,
    onClick: (newValue : String) -> Unit,
) {

    var startFolderPicker by rememberSaveable{
        mutableStateOf(false)
    }


    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClick = {
                    Log.i("Permission", "Folder Picker Started $startFolderPicker")
                    startFolderPicker = true
                },
                enabled = true
            ),
        value = folderDestination,
        readOnly = true,
        onValueChange = { newValue: String ->
            onClick("")
        },
        label = { Text("Folder") },
        textStyle = MaterialTheme.typography.labelSmall,
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Folder,
                contentDescription = "Leading Icon Folder",
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        shape = RoundedCornerShape(8.dp),
        enabled = false
    )

    if (startFolderPicker){
        Log.i("Permission","Folder Picker Started")
        FolderPicker(){
            onClick(it.toString())
            startFolderPicker = false
        }
    }

}


@Composable
fun InfoComposable(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription ="Info Icon",
            tint = MaterialTheme.colorScheme.tertiary,
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = "Where you want to save the mp3",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.tertiary,
        )
    }
}


@Composable
fun MainButton(
    modifier: Modifier = Modifier,
    textValue : String,
    onClick : () -> Unit
) {

    Button(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(10))
            .fillMaxWidth()
        ,
        onClick = onClick
    ) {
        Text(
            text = textValue,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall
        )
    }

}



@Preview(showBackground = true, widthDp = 360 , heightDp = 800)
@Composable
fun InitialScreenPreview(){
    Mp3DownloaderComposeTheme(
    ){

        Surface(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            InitialScreen()
        }

    }
}

