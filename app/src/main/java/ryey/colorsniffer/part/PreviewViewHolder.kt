package ryey.colorsniffer.part

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ryey.colorsniffer.ColoringMethod
import ryey.colorsniffer.R

class PreviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val textView_appName: TextView = itemView.findViewById(R.id.textView_appName)
    private val textView_className: TextView =  itemView.findViewById(R.id.textView_className)
    private val textView_packageName: TextView =  itemView.findViewById(R.id.textView_packageName)
    private val imageView_icon: ImageView = itemView.findViewById(R.id.imageView_icon)
    private val imageView_color: ImageView = itemView.findViewById(R.id.imageView_color)

    internal var visible = true
        set(value) {
            field = value
            itemView.visibility = if (value) View.VISIBLE else View.GONE
        }

    internal fun fillWith(launcherActivityInfo: LauncherActivityInfo, coloringMethod: ColoringMethod, defaultColor: Int) {
        if (!visible)
            return
        textView_appName.text = launcherActivityInfo.label
        textView_packageName.text = launcherActivityInfo.packageName
        textView_className.text = launcherActivityInfo.klass
        imageView_icon.setImageDrawable(launcherActivityInfo.icon)
        imageView_color.setImageDrawable(
            ColoringMethod.colorDrawable(
                launcherActivityInfo,
                coloringMethod,
                defaultColor
            )
        )
    }

}