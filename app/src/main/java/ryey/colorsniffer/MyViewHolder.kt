package ryey.colorsniffer

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val textView_appName: TextView = itemView.findViewById(R.id.textView_appName)
    val imageView: ImageView = itemView.findViewById(R.id.imageView)
    val imageView2: ImageView = itemView.findViewById(R.id.imageView2)

    fun fillWith(launcherActivityInfo: LauncherActivityInfo, coloringMethod: ColoringMethod) {
        textView_appName.text = launcherActivityInfo.name
        imageView.setImageDrawable(launcherActivityInfo.icon)
        imageView2.setImageDrawable(ColoringMethod.colorDrawable(launcherActivityInfo, coloringMethod))
    }

}