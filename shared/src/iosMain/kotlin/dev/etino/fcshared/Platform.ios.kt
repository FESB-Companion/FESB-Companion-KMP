package dev.etino.fcshared

import platform.UIKit.UIDevice
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

class IOSPlatform : Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()


actual fun openUrl(url: String) {
    NSURL.URLWithString(url)?.let {
        UIApplication.sharedApplication.openURL(
            it,
            options = emptyMap<Any?, Any?>(),
            completionHandler = null
        )
    }
}
