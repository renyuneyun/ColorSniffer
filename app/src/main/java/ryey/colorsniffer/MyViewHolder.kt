package ryey.colorsniffer

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val imageView: ImageView = itemView.findViewById(R.id.imageView)
    val imageView2: ImageView = itemView.findViewById(R.id.imageView2)

    fun fillWith(appInfo: AppInfo, coloringMethod: ColoringMethod) {
        imageView.setImageDrawable(appInfo.icon)
        imageView2.setImageDrawable(ColoringMethod.colorDrawable(appInfo, coloringMethod))
    }

}