package com.core.base.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import okhttp3.Cache
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale


fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun Context.isConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
    return if (connectivityManager is ConnectivityManager) {
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        networkInfo?.isConnected ?: false
    } else false
}

fun Context.provideCache(): Cache {
    val cacheSize = (1 * 1024 * 1024).toLong() // 1 Mb
    return Cache(
            File(cacheDir, "cache_source"),
            cacheSize
    )
}

inline fun <R> R?.orElse(block: () -> R): R {
    return this ?: block()
}

val publishedAtServerFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale("en", "in"))

private val lastSyncUIFormat =
        SimpleDateFormat("EEE, d MMM yyyy 'at' hh:mm aaa", Locale("en", "in"))

fun String.timeFormatForPublished(): String {
    val date = publishedAtServerFormat.parse(this)
    return lastSyncUIFormat.format(date)
}

fun Fragment.tryNavigate(directions: NavDirections) {
    try {
        findNavController().navigate(directions)
    } catch (ex: Exception) {
        Log.d("error", "don't press too quick")
    }
}

fun String?.thisOrEmpty() = this?.let {
    if (it.isNotEmpty()) it
    else ""
}.orElse { "" }