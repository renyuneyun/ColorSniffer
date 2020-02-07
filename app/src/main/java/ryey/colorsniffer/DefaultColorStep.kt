package ryey.colorsniffer

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.rarepebble.colorpicker.ColorPickerView
import ernestoyaquello.com.verticalstepperform.Step

class DefaultColorStep : Step<Int>("Default color") {

    var defaultColor: Int = Color.BLACK
        private set(value) {
            field = value
            imageViewDefaultColor?.setImageDrawable(ColorDrawable(value))
        }
    private var imageViewDefaultColor: ImageView? = null

    override fun restoreStepData(data: Int?) {
        if (data != null) {
            defaultColor = data
        }
    }

    override fun isStepDataValid(stepData: Int?): IsDataValid {
        return IsDataValid(stepData != null)
    }

    override fun onStepMarkedAsCompleted(animated: Boolean) {
    }

    override fun getStepDataAsHumanReadableString(): String {
        return "0x%X".format(defaultColor)
    }

    override fun createStepContentLayout(): View {
        val view = LayoutInflater.from(context).inflate(R.layout.part_default_color, null)

        imageViewDefaultColor = view.findViewById(R.id.imageView_default_color)
        imageViewDefaultColor?.setOnClickListener {
            val colorPickerView = ColorPickerView(context)
            AlertDialog.Builder(context)
                .setView(colorPickerView)
                .setPositiveButton("Ok") { dialog, which ->
                    defaultColor = colorPickerView.color
                }
                .show()
        }

        return view
    }

    override fun getStepData(): Int {
        return defaultColor
    }

    override fun onStepOpened(animated: Boolean) {
    }

    override fun onStepMarkedAsUncompleted(animated: Boolean) {
    }

    override fun onStepClosed(animated: Boolean) {
    }
}