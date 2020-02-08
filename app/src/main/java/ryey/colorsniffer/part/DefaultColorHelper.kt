package ryey.colorsniffer.part

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.rarepebble.colorpicker.ColorPickerView

class DefaultColorHelper(val imageViewDefaultColor: ImageView) {

    var defaultColor: Int = Color.BLACK
        set(value) {
            field = value
            imageViewDefaultColor?.setImageDrawable(ColorDrawable(value))
        }

    init {
        imageViewDefaultColor.setOnClickListener {
            imageViewDefaultColor.context.let { context ->
                val colorPickerView = ColorPickerView(context)
                AlertDialog.Builder(context)
                    .setView(colorPickerView)
                    .setPositiveButton("Ok") { dialog, which ->
                        defaultColor = colorPickerView.color
                    }
                    .show()
            }
        }
    }

}