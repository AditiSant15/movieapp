package com.example.movieapp.Activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.movieapp.Domain.FilmItemModel
import com.example.movieapp.R


class DetailMovieActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve the movie item passed via Intent
        filmItem = (intent.getSerializableExtra("object") as FilmItemModel)

        setContent {
                DetailScreen(filmItem, onBackClick = {finish()})
        }
    }
}

@Composable
fun DetailScreen(item: FilmItemModel, onBackClick: () -> Unit) {

    val scrollState = rememberScrollState()
    val isLoading = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.blackBackground))
    ) {

        if (isLoading.value) {

            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )

        } else {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {

                Box(
                    modifier = Modifier
                        .height(400.dp)
                ) {

                    Image(
                        contentDescription = "",
                        painter = painterResource(id = R.drawable.back),
                        modifier = Modifier
                            .padding(start = 16.dp, top = 48.dp)
                            .clickable { onBackClick() }
                    )

                    Image(
                        contentDescription = "",
                        painter = painterResource(id = R.drawable.back),
                        modifier = Modifier
                            .padding(start = 16.dp, top = 48.dp)
                            .align(Alignment.TopEnd)
                    )

                    AsyncImage(
                        model = item.Poster,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        alpha = 0.1f
                    )

                    AsyncImage(
                        model = item.Poster,
                        contentDescription = null,
                        modifier = Modifier
                            .size(210.dp, 300.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .align(Alignment.BottomCenter),
                        contentScale = ContentScale.Crop
                    )

                    Box(
                        modifier = Modifier
                            .height(100.dp)
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        colorResource(R.color.black2),
                                        colorResource(R.color.black1)
                                    ),
                                    start = Offset(0f, 0f),
                                    end = Offset(0f, Float.POSITIVE_INFINITY)
                                )
                            )
                    )

                    Text(
                        text = item.Title,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 27.sp
                        ),
                        modifier = Modifier
                            .padding(top = 48.dp)
                            .align(Alignment.BottomCenter)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            painter = painterResource(R.drawable.star),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        Text(
                            text = "IMDB: ${item.Imdb}",
                            color = Color.White
                        )

                        Icon(
                            painter = painterResource(R.drawable.time),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        Text(
                            text = "Runtime: ${item.Time}",
                            color = Color.White
                        )

                        Icon(
                            painter = painterResource(R.drawable.cal),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        Text(
                            text = "Release: ${item.Year}",
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Summary",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = item.Description,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Actors",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(item.Casts.size) {

                            item.Casts[it].Actor?.let { actor ->

                                Text(
                                    text = "$actor, ",
                                    color = Color.White,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }

                LazyRow(
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(item.Casts.size) {

                        AsyncImage(
                            model = item.Casts[it].PicUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .size(75.dp)
                                .padding(4.dp)
                                .clip(RoundedCornerShape(50.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}
