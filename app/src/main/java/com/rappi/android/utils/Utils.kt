package com.rappi.android.utils

import android.app.Activity
import android.content.Intent

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