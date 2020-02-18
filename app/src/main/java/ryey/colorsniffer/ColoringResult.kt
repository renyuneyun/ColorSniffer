package ryey.colorsniffer

import android.os.Bundle
import ryey.colorsniffer.part.LauncherActivityInfo

class ColoringResult private constructor(
    val defaultColor: Int,
    val coloringMethod: ColoringMethod,
    private val color: Map<LauncherId, Int>
) {

    constructor(
        defaultColor: Int,
        coloringMethod: ColoringMethod,
        launcherActivityInfoList: List<LauncherActivityInfo>
    ) : this(defaultColor, coloringMethod, HashMap<LauncherId, Int>().apply {
        for (launcherActivityInfo in launcherActivityInfoList) {
            this[LauncherId(launcherActivityInfo)] =
                ColoringMethod.color(launcherActivityInfo, coloringMethod, defaultColor)
        }
    }
    )

    fun asIPCBundle(): Bundle {
        val extras = Bundle()
        extras.putBundle(IPC.KEY_COLOR_BUNDLE, Bundle().apply {
            putInt(IPC.KEY_DEFAULT_COLOR, defaultColor)
            for ((id, color) in color) {
                putInt(id.toString(), color)
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

    private data class LauncherId(val appId: String, val activityClass: String) {
        constructor(launcherActivityInfo: LauncherActivityInfo) : this(
            launcherActivityInfo.packageName,
            launcherActivityInfo.klass
        )

        override fun toString(): String {
            return "%s/%s".format(appId, activityClass)
        }

        companion object {
            fun from(repr: String): LauncherId? {
                val parts = repr.split("/")
                if (parts.size != 2 || parts[0].isBlank() || parts[1].isBlank())
                    return null
                return LauncherId(parts[0], parts[1])
            }
        }
    }

    class Partial(
        var defaultColor: Int? = null,
        var coloringMethod: ColoringMethod? = null
    ) {

        private var color: Map<LauncherId, Int>? = null

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
                val coloring: HashMap<LauncherId, Int> = HashMap()
                extras.getBundle(IPC.KEY_COLOR_BUNDLE)?.let {
                    for (appId in it.keySet()) {
                        val color = it.getInt(appId)
                        LauncherId.from(appId)?.let { id ->
                            coloring[id] = color
                        }
                    }
                }
                return Partial(defaultColor).apply {
                    color = coloring
                }
            }
        }
    }

}