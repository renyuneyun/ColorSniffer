package ryey.colorsniffer.part

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.drawable.Drawable

class LauncherActivityInfo {

    val packageName: String
    val label: CharSequence
    val klass: String
    val hasIcon: Boolean
    val icon: Drawable // The icon of the activity/app, or the system default icon

    constructor(context: Context, activityInfo: ActivityInfo) {
        val pm = context.packageManager
        packageName = activityInfo.packageName
        label = activityInfo.loadLabel(pm)
        klass = activityInfo.name
        hasIcon = activityInfo.iconResource != 0
        icon = activityInfo.loadIcon(pm)
    }
}