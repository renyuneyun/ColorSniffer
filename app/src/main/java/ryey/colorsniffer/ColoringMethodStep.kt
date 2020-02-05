package ryey.colorsniffer

import android.view.LayoutInflater
import android.view.View
import android.widget.RadioGroup
import ernestoyaquello.com.verticalstepperform.Step

class ColoringMethodStep: Step<ColoringMethod>("Choose coloring method") {

    private var coloringMethod: ColoringMethod  = ColoringMethod.dominantColor

    override fun createStepContentLayout(): View {
        val view = LayoutInflater.from(context).inflate(R.layout.part_color_method, null)

        view.findViewById<RadioGroup>(R.id.radioGroup).setOnCheckedChangeListener { group, checkedId ->
            for (pair in mapping) {
                if (pair.second == checkedId) {
                    coloringMethod = pair.first
                }
            }
        }

        return view
    }

    override fun getStepData(): ColoringMethod {
        return coloringMethod
    }

    override fun isStepDataValid(stepData: ColoringMethod?): IsDataValid {
        val isValid = stepData != null
        return IsDataValid(isValid, if (isValid) {"Coloring method not chosen"} else {""})
    }

    override fun getStepDataAsHumanReadableString(): String {
        return coloringMethod.toString()
    }

    override fun onStepClosed(animated: Boolean) {
    }

    override fun onStepMarkedAsCompleted(animated: Boolean) {
    }

    override fun onStepMarkedAsUncompleted(animated: Boolean) {
    }

    override fun onStepOpened(animated: Boolean) {
    }

    override fun restoreStepData(data: ColoringMethod?) {
        for (pair in mapping) {
            if (pair.first == data) {
                formView.findViewById<RadioGroup>(R.id.radioGroup).check(pair.second)
                break
            }
        }
    }

    companion object {
        val mapping = arrayOf(
            ColoringMethod.dominantColor to R.id.radioButton_dominate,
            ColoringMethod.random to R.id.radioButton_random,
            ColoringMethod.vibrantColor to R.id.radioButton_vibrant
        )
    }
}