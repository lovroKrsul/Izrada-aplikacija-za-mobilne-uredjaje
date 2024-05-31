package hr.algebra.Projectapp.framework

import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.getSystemService
import androidx.preference.PreferenceManager
import hr.algebra.Projectapp.PROVIDER_CONTENT_URI
import hr.algebra.Projectapp.model.Item

fun View.applyAnimation(animationId: Int) =
    startAnimation(AnimationUtils.loadAnimation(context, animationId))

inline fun <reified T : Activity> Context.startActivity() =
    startActivity(Intent(this, T::class.java)
        .apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
inline fun <reified T : Activity> Context.startActivity(key: String,value: Int) =
    startActivity(Intent(this, T::class.java)
        .apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra(key,value)
        })

inline fun <reified T : BroadcastReceiver> Context.sendBroadcast() =
    sendBroadcast(Intent(this, T::class.java))

fun Context.setBooleanPreference(key: String, value: Boolean = true)
        = PreferenceManager.getDefaultSharedPreferences(this)
    .edit()
    .putBoolean(key, value)
    .apply()

fun Context.getBooleanPreference(key: String)
        = PreferenceManager.getDefaultSharedPreferences(this)
    .getBoolean(key, false)

fun callDelayed(delay: Long, work: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(
        work,
        delay
    )
}

fun Context.isOnline() : Boolean {
    val connectivityManager = getSystemService<ConnectivityManager>()
    connectivityManager?.activeNetwork?.let {network ->
        connectivityManager.getNetworkCapabilities(network)?.let {networkCapabilities ->
            return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        }
    }
    return false
}

@SuppressLint("Range")
fun Context.fetchItems() : MutableList<Item> {
    val items = mutableListOf<Item>()

    val cursor = contentResolver.query(
        PROVIDER_CONTENT_URI,
        null,
        null,
        null,
        null
    )
    while (cursor != null && cursor.moveToNext()) {
        items.add(Item(
            cursor.getLong(cursor.getColumnIndexOrThrow(Item::_id.name)),
            cursor.getString(cursor.getColumnIndexOrThrow(Item::_userId.name)),
            cursor.getString(cursor.getColumnIndexOrThrow(Item::title.name)),
            cursor.getString(cursor.getColumnIndexOrThrow(Item::body.name)),
            cursor.getInt(cursor.getColumnIndexOrThrow(Item::read.name)) == 1
        ))
    }

    return items
}