package com.rappi.android

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
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
import com.rappi.android.ui.theme.Typography
import com.rappi.android.ui.theme.dmSansFamily
import com.rappi.android.ui.viewmodel.MainViewModel
import com.rappi.android.utils.hideKeyboard
import com.rappi.android.utils.showKeyboard

class SearchActivity: AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showKeyboard()
        setContent {
            val textState = remember { mutableStateOf(TextFieldValue("")) }
            Scaffold(backgroundColor = Color.Black) {
                Box {
                    MovieList(movieList = mainViewModel.moviesSearched)
                    TopBar(textState)
                }
            }
        }
    }

    @Composable
    fun TopBar(state: MutableState<TextFieldValue>) {
        TopAppBar(
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            modifier = Modifier.background(
                Brush.verticalGradient(
                listOf(Color.Black, colorResource(id = R.color.black_50), Color.Transparent)
            ))
        ) {
            SearchView(state = state)
        }
    }

    @Composable
    fun SearchView(state: MutableState<TextFieldValue>) {
        val focusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current

        TextField(
            value = state.value,
            onValueChange = { value ->
                state.value = value
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            textStyle = TextStyle(color = Color.White, fontSize = 16.sp, fontFamily = dmSansFamily, fontWeight = FontWeight.Normal),
            placeholder = { Text("Busque una película", color = colorResource(id = R.color.white_50), style = Typography.body2) },
            leadingIcon = {
                IconButton(onClick = {
                    focusManager.clearFocus()
                    hideKeyboard(currentFocus ?: View(this))
                    onBackPressed()
                }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp),
                        tint = Color.White
                    )
                }
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
                            tint = colorResource(id = R.color.white_50)
                        )
                    }
                }
            },
            singleLine = true,
            shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                cursorColor = Color.White,
                leadingIconColor = Color.White,
                trailingIconColor = Color.White,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                focusManager.clearFocus()
                hideKeyboard(currentFocus ?: View(this@SearchActivity))
                mainViewModel.fetchSearchMovies(state.value.text)
            })
        )

        DisposableEffect(Unit) {
            focusRequester.requestFocus()
            onDispose { }
        }
    }
}