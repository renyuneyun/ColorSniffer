package ryey.colorsniffer

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.drawable.Drawable

class LauncherActivityInfo {

    val packageName: String
    val label: CharSequence
    val klass: String
    val icon: Drawable

    constructor(context: Context, activityInfo: ActivityInfo) {
        val pm = context.packageManager
        packageName = activityInfo.packageName
        label = activityInfo.loadLabel(pm)
        klass = activityInfo.name
        icon = activityInfo.loadIcon(pm)
    }
}