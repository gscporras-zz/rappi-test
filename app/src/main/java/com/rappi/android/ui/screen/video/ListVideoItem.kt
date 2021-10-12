package com.rappi.android.ui.screen.video
/*

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.rappi.android.models.response.VideoItem
import com.rappi.android.utils.BASE_FORMAT_IMAGE_URL
import com.rappi.android.utils.BASE_Y2_IMAGE_URL
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.rappi.android.R


@Composable
fun ListVideoItem(videoItem: VideoItem, modifier: Modifier) {
    val context = LocalContext.current as? Context
    VideoItem(videoItem = videoItem, modifier = modifier
        //.padding(8.dp)
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
fun VideoItem(
    videoItem: VideoItem, modifier: Modifier
) {
    Card(
        modifier = modifier,
        elevation = 0.dp,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = colorResource(id = R.color.black_50)
    ) {
        Box {
            VideoImage(imagePath = videoItem.key ?: "")
            Column(
                modifier = Modifier
                    .size(320.dp, 180.dp)
                    .background(
                        colorResource(id = R.color.black_30)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier.size(50.dp),
                    painter = painterResource(id = R.drawable.ic_youtube),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        }
    }
}

@Composable
fun VideoImage(imagePath: String) {
    Image(
        modifier = Modifier
            .size(320.dp, 180.dp),
        painter = rememberCoilPainter(request = BASE_Y2_IMAGE_URL+imagePath+ BASE_FORMAT_IMAGE_URL),
        contentDescription = "",
        contentScale = ContentScale.Crop
    )
}*/
