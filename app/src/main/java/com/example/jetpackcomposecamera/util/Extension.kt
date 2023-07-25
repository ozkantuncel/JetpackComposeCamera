package com.example.jetpackcomposecamera.util

import android.app.Activity
import android.view.View
import android.content.Context
import android.os.Environment
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.jetpackcomposecamera.R
import java.io.File




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

fun Context.mkDir():File{
    return this.getExternalFilesDirs(Environment.DIRECTORY_PICTURES).firstOrNull()?.let {
        File(it, this.resources.getString(R.string.takenPhoto)).apply { mkdirs() }
    } ?: error("Cannot create directory")
}

/**
 *Context nesnesini kullanarak resimlerin saklandığı dizini bulur
 */

fun Context.mkDirControl():File{
    val mediaDir = this.getExternalFilesDirs(Environment.DIRECTORY_PICTURES).firstOrNull()?.let {
        File(it, this.resources.getString(R.string.takenPhoto)).apply { mkdirs() }
    }
    return if (mediaDir != null && mediaDir.exists()) mediaDir else this.filesDir
}