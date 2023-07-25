package com.example.jetpackcomposecamera.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Environment
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.jetpackcomposecamera.R
import java.io.File

/**
 * Ilgili sayfadaki context uzerinden activity almamizi saglar.
 */
inline fun <reified Activity : ComponentActivity> Context.getActivity(): Activity? {
    return when (this) {
        is Activity -> this
        else -> {
            var context = this
            while (context is ContextWrapper) {
                context = context.baseContext
                if (context is Activity) return context
            }
            null
        }
    }
}



/**
 * Klavyeyi kapatmamizi saglar.
 */


fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}


fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

/**
 * Context uzerinden toast mesaj gondermemizi saglar.
 */
fun Context.toast(message: String?, length: Int = Toast.LENGTH_SHORT) {
    message?.let {
        Toast.makeText(this, message, length).show()
    }
}

/**
 * Context uzerinden uygulama resim yolunu cekmeyi saglar
 */

fun Context.mkDir():File = this.getExternalFilesDirs(Environment.DIRECTORY_PICTURES).firstOrNull()?.let {
    File(it, this.resources.getString(R.string.takenPhoto)).apply { mkdirs() }
} ?: error("Cannot create directory")


/**
 *Context nesnesini kullanarak resimlerin saklandığı dizini bulur
 */

fun Context.mkDirControl(): File = this.getExternalFilesDirs(Environment.DIRECTORY_PICTURES).firstOrNull()?.let {
    File(it, this.resources.getString(R.string.takenPhoto)).apply { mkdirs() }
} ?: this.filesDir