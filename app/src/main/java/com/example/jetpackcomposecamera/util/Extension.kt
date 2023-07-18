package com.example.jetpackcomposecamera.util

import android.content.Context
import android.content.ContextWrapper
import android.os.Environment
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



