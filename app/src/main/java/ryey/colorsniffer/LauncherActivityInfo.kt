package ryey.colorsniffer

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.drawable.Drawable

class LauncherActivityInfo {

    val name: String
    val icon: Drawable

    constructor(context: Context, activityInfo: ActivityInfo) {
        name = activityInfo.name
        icon = activityInfo.loadIcon(context.packageManager)
    }
}