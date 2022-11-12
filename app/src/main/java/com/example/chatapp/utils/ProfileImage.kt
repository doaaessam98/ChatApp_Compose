package com.example.chatapp.utils

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.chatapp.R

@Composable
fun DefaultProfilePicture(modifier: Modifier,name: String) {
    Box(modifier.background(Color.Gray, shape = CircleShape)) {
        Icon(
            imageVector = Icons.Rounded.Person,
            contentDescription = stringResource(
                id = R.string.search_profile_image_description,
                formatArgs = arrayOf()
            ),
            modifier = modifier.align(Alignment.Center)
                //.padding(end = 8.dp, top = 8.dp, bottom = 8.dp)
                .clip(CircleShape)
                .requiredSize(Constants.PROFILE_PICTURE_SIZE.dp),
            tint = Color.White
        )
    }
}
@Composable
fun ProfilePicture(
    picture: Bitmap?,
    name: String
) {
    Image(
        bitmap = picture!!.asImageBitmap(),
        contentDescription = stringResource(id = R.string.search_profile_image_description,
            formatArgs = arrayOf(name)),
        modifier = Modifier
            .padding(horizontal = 2.dp, vertical = 2.dp)
            .clip(CircleShape)
            .requiredSize(Constants.PROFILE_PICTURE_SIZE.dp)
    )
}