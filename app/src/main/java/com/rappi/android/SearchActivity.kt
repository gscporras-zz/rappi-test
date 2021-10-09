package com.rappi.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.ImeOptions
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rappi.android.data.model.MovieItem
import com.rappi.android.ui.app.list.MovieList
import com.rappi.android.ui.theme.dmSansFamily
import com.rappi.android.ui.viewmodel.MainViewModel

class SearchActivity: AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val textState = remember { mutableStateOf(TextFieldValue("")) }
            Scaffold(
                topBar = { TopBar(textState) }
            ) {
                MovieList(context = this, movieList = mainViewModel.moviesSearched)
            }
        }
    }

    @Composable
    fun TopBar(state: MutableState<TextFieldValue>) {
        TopAppBar(
            backgroundColor = colorResource(id = R.color.white),
        ) {
            SearchView(state = state)
        }
    }

    @Composable
    fun SearchView(state: MutableState<TextFieldValue>) {
        TextField(
            value = state.value,
            onValueChange = { value ->
                state.value = value
            },
            modifier = Modifier
                .fillMaxWidth(),
            textStyle = TextStyle(color = colorResource(id = R.color.black), fontSize = 16.sp, fontFamily = dmSansFamily, fontWeight = FontWeight.Normal),
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(15.dp)
                        .size(24.dp),
                    tint = colorResource(id = R.color.black_50),
                )
            },
            trailingIcon = {
                if (state.value != TextFieldValue("")) {
                    IconButton(
                        onClick = {
                            state.value =
                                TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                        }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(15.dp)
                                .size(24.dp),
                            tint = colorResource(id = R.color.black_50)
                        )
                    }
                }
            },
            singleLine = true,
            shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(id = R.color.black),
                cursorColor = colorResource(id = R.color.black),
                leadingIconColor = Color.White,
                trailingIconColor = Color.White,
                backgroundColor = colorResource(id = R.color.white),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { mainViewModel.fetchSearchMovies(state.value.text) })
        )
    }
}