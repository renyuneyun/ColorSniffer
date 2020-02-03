package ryey.colorsniffer

import android.content.Context
import android.content.pm.ApplicationInfo
import android.graphics.drawable.Drawable

class AppInfo {

    val name: String
    val icon: Drawable

    constructor(context: Context, packageInfo: ApplicationInfo) {
        name = packageInfo.name
        icon = packageInfo.loadIcon(context.packageManager)
    }
}