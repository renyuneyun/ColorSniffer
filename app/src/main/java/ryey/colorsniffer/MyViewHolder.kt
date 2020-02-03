package ryey.colorsniffer

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val textView_appName: TextView = itemView.findViewById(R.id.textView_appName)
    val imageView: ImageView = itemView.findViewById(R.id.imageView)
    val imageView2: ImageView = itemView.findViewById(R.id.imageView2)

    fun fillWith(appInfo: AppInfo, coloringMethod: ColoringMethod) {
        textView_appName.text = appInfo.name
        imageView.setImageDrawable(appInfo.icon)
        imageView2.setImageDrawable(ColoringMethod.colorDrawable(appInfo, coloringMethod))
    }

}