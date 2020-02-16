package ryey.colorsniffer

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import ryey.colorsniffer.part.LauncherActivityInfo
import java.util.*


enum class ColoringMethod {
    dominantColor,
    random,
    vibrantColor,
    ;

    companion object {

        val DEFAULT = dominantColor

        private fun colorPalette(launcherActivityInfo: LauncherActivityInfo): Palette {
            return Palette.from(launcherActivityInfo.icon.toBitmap()).generate()
        }

        fun color(launcherActivityInfo: LauncherActivityInfo, coloringMethod: ColoringMethod, defaultColor: Int): Int {
            val colorOrDefault = fun (extract : (Palette) -> Int): Int {
                return if (!launcherActivityInfo.hasIcon)
                    defaultColor
                else
                    extract(colorPalette(launcherActivityInfo))
            }
            return when (coloringMethod) {
                dominantColor -> {
                    colorOrDefault {
                        it.getDominantColor(defaultColor)
                    }
                }
                random -> {
                    val rnd = Random()
                    Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
                }
                vibrantColor -> {
                    colorOrDefault {
                        it.getVibrantColor(defaultColor)
                    }
                }
            }
        }

        fun colorDrawable(launcherActivityInfo: LauncherActivityInfo, coloringMethod: ColoringMethod, defaultColor: Int): ColorDrawable {
            return ColorDrawable(color(launcherActivityInfo, coloringMethod, defaultColor))
        }
    }
}