package com.rappi.android.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService

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