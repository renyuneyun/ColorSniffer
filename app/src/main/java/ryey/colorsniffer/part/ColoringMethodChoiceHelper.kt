package ryey.colorsniffer.part

import android.widget.RadioGroup
import ryey.colorsniffer.ColoringMethod
import ryey.colorsniffer.R

class ColoringMethodChoiceHelper(private val radioGroup: RadioGroup, private val coloringMethodChangeListener: ColoringMethodChangeListener? = null) {

    constructor(radioGroup: RadioGroup, onColoringMethodChanged: (coloringMethod: ColoringMethod) -> Unit): this(radioGroup, object : ColoringMethodChangeListener {
        override fun onChanged(coloringMethod: ColoringMethod) {
            onColoringMethodChanged(coloringMethod)
        }
    })

    var coloringMethod: ColoringMethod =
        ColoringMethod.DEFAULT
        set(value) {
            if (value == field)
                return
            field = value
            for (pair in mapping) {
                if (pair.first == value) {
                    radioGroup.check(pair.second)
                    break
                }
            }
            coloringMethodChangeListener?.onChanged(coloringMethod)
        }

    init {
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            for (pair in mapping) {
                if (pair.second == checkedId) {
                    coloringMethod = pair.first
                    break
                }
            }
        }
    }

    interface ColoringMethodChangeListener {
        fun onChanged(coloringMethod: ColoringMethod)
    }

    companion object {
        private val mapping = arrayOf(
            ColoringMethod.dominantColor to R.id.radioButton_dominate,
            ColoringMethod.random to R.id.radioButton_random,
            ColoringMethod.vibrantColor to R.id.radioButton_vibrant
        )
    }
}