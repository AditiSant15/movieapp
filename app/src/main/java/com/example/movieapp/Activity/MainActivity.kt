package com.example.movieapp.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.movieapp.BottomNavigationBar
import com.example.movieapp.Domain.FilmItemModel
import com.example.movieapp.FilmItem
import com.example.movieapp.R
import com.example.movieapp.SearchBar
import com.example.movieapp.ViewModel.MainViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen(onItemClick = {
            val intent= Intent(this, DetailMovieActivity::class.java)
            intent.putExtra("object",item)
            startActivity(intent)

            })

        }
    }
}
@Preview
@Composable
fun MainScreen(onItemClick: (FilmItemModel) -> Unit = {}) {

    Scaffold(
        bottomBar = { BottomNavigationBar() },

        floatingActionButton = {

            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                colorResource(R.color.pink),
                                colorResource(R.color.green)
                            )
                        ),
                        shape = CircleShape
                    )
                    .padding(3.dp)
            ) {

                FloatingActionButton(
                    onClick = {},
                    backgroundColor = colorResource(id = R.color.black3),
                    modifier = Modifier.size(58.dp),
                    contentColor = Color.White,
                    content={
                Icon(
                    painter = painterResource(R.drawable.float_icon),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
                    }
                )
            }
        },

        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = androidx.compose.material.FabPosition.Center,
        backgroundColor = colorResource(R.color.blackBackground)

    ) { paddingValues ->

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .background(color = colorResource(R.color.blackBackground))
        )
        {

            Image(
                painter = painterResource(id = R.drawable.bg1),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
        }
        MainContent(onItemClick)
    }
}

@Composable
fun MainContent(onItemClick: (FilmItemModel) -> Unit) {
val viewModel = MainViewModel()
    val upcoming = remember { mutableStateListOf<FilmItemModel>() }
    val newMovies = remember { mutableStateListOf<FilmItemModel>() }

    var showUpcomingLoad by remember { mutableStateOf(true) }
    var showNewMoviesLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.loadUpcoming().observeForever {
            upcoming.clear()
            upcoming.addAll(it)
            showUpcomingLoad = false
        }
    }
    LaunchedEffect(Unit) {
        viewModel.loadItems().observeForever {
            newMovies.clear()
            newMovies.addAll(it)
            showNewMoviesLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 60.dp, bottom = 100.dp)
    ) {

        Text(
            text = "What would you like to watch?",
            style = TextStyle(color = Color.White, fontSize = 25.sp),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(start = 16.dp, bottom = 16.dp)
                .fillMaxWidth()
        )

        SearchBar(hint = "Search Movies....")
        SectionTitle(title = "New Movies")

        if (showNewMoviesLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(newMovies) { item ->
                    FilmItem(item, onItemClick)
                }
            }
        }
        SectionTitle("Upcoming Movies")

        if (showUpcomingLoad) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(upcoming) { item ->
                    FilmItem(item, onItemClick)
                }
            }
        }



    }
}
@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = TextStyle(color = Color(0xfffffc107), fontSize = 18.sp),
        modifier = Modifier.padding(start = 16.dp, top = 32.dp, bottom = 8.dp),
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
    )
}
