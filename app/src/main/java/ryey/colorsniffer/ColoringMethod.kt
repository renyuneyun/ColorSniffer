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
    ;

    companion object {
        fun color(appInfo: AppInfo, coloringMethod: ColoringMethod): Int {
            return when (coloringMethod) {
                dominantColor -> {
                    val p = Palette.from(appInfo.icon.toBitmap()).generate()
                    p.getDominantColor(
                        ResourcesCompat.getColor(Resources.getSystem(), android.R.color.black, null)
                    )
                }
                random -> {
                    val rnd = Random()
                    Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
                }
            }
        }

        fun colorDrawable(appInfo: AppInfo, coloringMethod: ColoringMethod): ColorDrawable {
            return ColorDrawable(color(appInfo, coloringMethod))
        }
    }
}