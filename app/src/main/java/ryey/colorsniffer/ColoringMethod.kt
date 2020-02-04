package ryey.colorsniffer

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import java.util.*


enum class ColoringMethod {
    dominantColor,
    random,
    vibrantColor,
    ;

    companion object {

        private val defaultColor = ResourcesCompat.getColor(Resources.getSystem(), android.R.color.black, null)

        fun color(launcherActivityInfo: LauncherActivityInfo, coloringMethod: ColoringMethod): Int {
            return when (coloringMethod) {
                dominantColor -> {
                    val p = Palette.from(launcherActivityInfo.icon.toBitmap()).generate()
                    p.getDominantColor(defaultColor)
                }
                random -> {
                    val rnd = Random()
                    Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
                }
                vibrantColor -> {
                    val p = Palette.from(launcherActivityInfo.icon.toBitmap()).generate()
                    p.getVibrantColor(defaultColor)
                }
            }
        }

        fun colorDrawable(launcherActivityInfo: LauncherActivityInfo, coloringMethod: ColoringMethod): ColorDrawable {
            return ColorDrawable(color(launcherActivityInfo, coloringMethod))
        }
    }
}