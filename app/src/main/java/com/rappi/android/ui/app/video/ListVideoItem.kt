package com.rappi.android.ui.app.video

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.rappi.android.R
import com.rappi.android.data.model.VideoItem
import com.rappi.android.utils.BASE_FORMAT_IMAGE_URL
import com.rappi.android.utils.BASE_IMAGE_URL
import com.rappi.android.utils.BASE_Y2_IMAGE_URL
import android.content.ActivityNotFoundException

import android.R.id
import android.content.Context

import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext


@Composable
fun ListVideoItem(videoItem: VideoItem) {
    val context = LocalContext.current as? Context
    ListVideoItem(videoItem = videoItem, modifier = Modifier
        .padding(8.dp)
        .clickable {
            val id = videoItem.key
            val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
            val webIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=$id")
            )
            try {
                context?.startActivity(appIntent)
            } catch (ex: ActivityNotFoundException) {
                context?.startActivity(webIntent)
            }
        }
    )
}

@Composable
fun ListVideoItem(
    videoItem: VideoItem, modifier: Modifier
) {
    Card(
        modifier = modifier,
        elevation = 0.dp,
        shape = RoundedCornerShape(16.dp),
        backgroundColor = colorResource(id = R.color.black_3)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            VideoImage(imagePath = videoItem.key ?: "")
        }
    }
}

@Composable
fun VideoImage(imagePath: String) {
    Image(
        modifier = Modifier
            .width(250.dp)
            .height(140.dp), painter = rememberCoilPainter(
            request = BASE_Y2_IMAGE_URL+imagePath+ BASE_FORMAT_IMAGE_URL
        ),
        contentDescription = "",
        contentScale = ContentScale.Crop
    )
}