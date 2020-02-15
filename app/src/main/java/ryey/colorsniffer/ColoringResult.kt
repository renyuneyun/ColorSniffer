package ryey.colorsniffer

import android.os.Bundle
import ryey.colorsniffer.part.LauncherActivityInfo

class ColoringResult(
    val defaultColor: Int,
    val coloringMethod: ColoringMethod,
    val color: Map<String, Int>
) {

    constructor(
        defaultColor: Int,
        coloringMethod: ColoringMethod,
        launcherActivityInfoList: List<LauncherActivityInfo>
    ) : this(defaultColor, coloringMethod, HashMap<String, Int>().apply {
        for (launcherActivityInfo in launcherActivityInfoList) {
            this[launcherActivityInfo.packageName] =
                ColoringMethod.color(launcherActivityInfo, coloringMethod)
        }
    }
    )

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

    class Partial(
        var defaultColor: Int? = null,
        var coloringMethod: ColoringMethod? = null,
        var color: Map<String, Int>? = null
    ) {
        fun asColoringResult(): ColoringResult? {
            defaultColor?.let { defaultColor ->
                coloringMethod?.let { coloringMethod ->
                    color?.let { color ->
                        if (color.isNotEmpty())
                            return ColoringResult(defaultColor, coloringMethod, color)
                    }
                }
            }
            return null
        }

        companion object {
            fun fromExtras(extras: Bundle): Partial {
                val defaultColor = if (extras.containsKey(IPC.KEY_DEFAULT_COLOR)) {
                    extras.getInt(IPC.KEY_DEFAULT_COLOR)
                } else {
                    null
                }
                val coloring: HashMap<String, Int> = HashMap()
                extras.getBundle(IPC.KEY_COLOR_BUNDLE)?.let {
                    for (appId in it.keySet()) {
                        val color = it.getInt(appId)
                        coloring[appId] = color
                    }
                }
                return Partial(defaultColor, color = coloring)
            }
        }
    }

}