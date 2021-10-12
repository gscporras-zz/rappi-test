package com.rappi.android.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.*
import com.rappi.android.network.Api
import com.rappi.android.ui.navigation.NavScreen
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

inline fun <reified T : Activity> Activity.startActivity() {
    startActivity(Intent(this, T::class.java))
}


inline fun <reified T : Activity> Activity.startActivity(body: Intent.() -> Unit) {
    startActivity(intentFor<T>(body))
}


inline fun <reified T : Activity> Activity.intentFor(body: Intent.() -> Unit) =
    Intent(this, T::class.java).apply(body)


inline fun <reified T : Activity> Activity.startActivityWithFlags(body: Intent.() -> Unit) {
    startActivity(intentFor<T>(body).apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    })
}


inline fun <reified T : Activity> Activity.startActivityWithFlags() {
    startActivity(Intent(this, T::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    })
}

inline fun <reified T : Activity> Activity.startActivityForResult(requestCode: Int) {
    startActivityForResult(Intent(this, T::class.java), requestCode)
}


inline fun <reified T : Activity> Activity.startActivityForResult(requestCode: Int, body: Intent.() -> Unit) {
    startActivityForResult(intentFor<T>(body), requestCode)
}

fun Context.showKeyboard() {
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}


inline fun <T> LazyListScope.paging(
    items: List<T>,
    currentIndexFlow: StateFlow<Int>,
    threshold: Int = 4,
    pageSize: Int = Api.PAGING_SIZE,
    crossinline fetch: () -> Unit,
    crossinline itemContent: @Composable LazyItemScope.(item: T) -> Unit,
) {
    itemsIndexed(items) { index, item ->

        itemContent(item)

        if ((index + threshold + 1) >= pageSize * (currentIndexFlow.value - 1)) {
            fetch()
        }
    }
}

inline fun <T> LazyGridScope.pagingGrid(
    items: List<T>,
    currentIndexFlow: StateFlow<Int>,
    threshold: Int = 4,
    pageSize: Int = Api.PAGING_SIZE,
    crossinline fetch: () -> Unit,
    crossinline itemContent: @Composable LazyItemScope.(item: T) -> Unit,
) {
    itemsIndexed(items) { index, item ->

        itemContent(item)

        if ((index + threshold + 1) >= pageSize * (currentIndexFlow.value - 1)) {
            fetch()
        }
    }
}

fun String.fromRoute() : String {
    var title = ""
    when(this) {
        NavScreen.Home.route -> title = ""
        NavScreen.PopularDetails.route -> title = "Popular"
        NavScreen.TopRatedDetails.route -> title = "TopRated"
    }
    return title
}