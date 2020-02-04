package ryey.colorsniffer

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.drawable.Drawable

class LauncherActivityInfo {

    val name: CharSequence
    val klass: String
    val icon: Drawable

    constructor(context: Context, activityInfo: ActivityInfo) {
        val pm = context.packageManager
        name = activityInfo.loadLabel(pm)
        klass = activityInfo.name
        icon = activityInfo.loadIcon(pm)
    }
}