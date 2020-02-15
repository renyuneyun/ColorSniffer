package ryey.colorsniffer

import android.os.Bundle
import ryey.colorsniffer.part.LauncherActivityInfo

class ColoringResult(
    val defaultColor: Int,
    val coloringMethod: ColoringMethod,
    launcherActivityInfoList: List<LauncherActivityInfo>
) {

    val color: Map<String, Int>

    init {
        val clr = HashMap<String, Int>()
        for (launcherActivityInfo in launcherActivityInfoList) {
            clr[launcherActivityInfo.packageName] = ColoringMethod.color(launcherActivityInfo, coloringMethod)
        }
        color = clr
    }

    fun asIPCBundle(): Bundle {
        val extras = Bundle()
        extras.putBundle(IPC.KEY_COLOR_BUNDLE, Bundle().apply {
            putInt(IPC.KEY_DEFAULT_COLOR, defaultColor)
            for ((appId, color) in color) {
                putInt(appId, color)
            }
        })
        return extras
    }

    fun asTSV(): String {
        val buf = StringBuilder()
        for ((appId, color) in color) {
            buf.append("%s\t#%X\n".format(appId, color))
        }
        return buf.toString()
    }

}