package dev.etino.fcshared


import android.os.Build
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

private lateinit var appContext: Context

fun initializeUrlOpener(context: Context) {
    appContext = context.applicationContext
}

actual fun openUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri()).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    appContext.startActivity(intent)
}

