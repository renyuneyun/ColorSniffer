package ryey.colorsniffer

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette

class App {

    val name: String
    val icon: Drawable
    val dominantColor: ColorDrawable

    constructor(context: Context, packageInfo: ApplicationInfo) {
        name = packageInfo.name
        icon = packageInfo.loadIcon(context.packageManager)
        val p = Palette.from(icon.toBitmap()).generate()
        dominantColor = ColorDrawable(
            p.getDominantColor(
                ResourcesCompat.getColor(Resources.getSystem(), android.R.color.black, null)
            ))
    }
}