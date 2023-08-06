package com.faisal.mp3downloadercompose.ui.common

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState

/**
 * Created By Faisal Abdirashid on Date : 7/8/2023.
 */


@Composable
fun MyTextView(
    modifier: Modifier = Modifier,
    textValue : String
) {
    Text(
        text = textValue,
        style = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Bold, color = Color.Black
        ),
        modifier = modifier.padding(top = 8.dp)
    )
}


@Composable
fun FolderPicker(onFolderSelected: (Uri) -> Unit) {

    val contentResolver = LocalContext.current.contentResolver

    val baseUri = getDocumentsFolderBaseUri(contentResolver)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocumentTree(),
        onResult = { uri: Uri? ->
            uri?.let { onFolderSelected(it) }
        }
    )

    LaunchedEffect(Unit){
        launcher.launch(baseUri)
    }

}





@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermission(
     permission : String,
     permissionNotGranted : () -> Unit,
     permissionNotAvailable : () -> Unit,
     permissionGiven : () -> Unit,
) {
    val permissionState = rememberPermissionState(permission = permission)


    LaunchedEffect(Unit){
        permissionState.launchPermissionRequest()
    }

    PermissionRequired(
        permissionState = permissionState,
        permissionNotGrantedContent = {
              permissionNotGranted()
        },
        permissionNotAvailableContent = {
            permissionNotAvailable()
        }) {
        permissionGiven()
    }

}



// Function to get the base URI for the Documents folder
fun getDocumentsFolderBaseUri(contentResolver: ContentResolver): Uri? {
    val collection = MediaStore.Files.getContentUri("external")
    val selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
    val selectionArgs = arrayOf(MediaStore.Files.FileColumns.MEDIA_TYPE_DOCUMENT.toString())

    val projection = arrayOf(MediaStore.Files.FileColumns._ID)

    val sortOrder = "${MediaStore.Files.FileColumns._ID} ASC"

    contentResolver.query(
        collection,
        projection,
        selection,
        selectionArgs,
        sortOrder
    )?.use { cursor ->
        if (cursor.moveToFirst()) {
            val idColumnIndex = cursor.getColumnIndex(MediaStore.Files.FileColumns._ID)
            val id = cursor.getLong(idColumnIndex)
            return ContentUris.withAppendedId(collection, id)
        }
    }

    return null
}

