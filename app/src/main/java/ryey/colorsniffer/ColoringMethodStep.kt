package ryey.colorsniffer

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import ernestoyaquello.com.verticalstepperform.Step
import ryey.colorsniffer.part.ColoringMethodChoiceHelper

class ColoringMethodStep(resources: Resources): Step<ColoringMethod>(resources.getString(R.string.title_choose_coloring_method)) {

    private lateinit var coloringMethodChoiceHelper: ColoringMethodChoiceHelper

    override fun createStepContentLayout(): View {
        val view = LayoutInflater.from(context).inflate(R.layout.part_color_method, null)

        coloringMethodChoiceHelper =
            ColoringMethodChoiceHelper(
                view.findViewById(R.id.radioGroup)
            )

        return view
    }

    override fun getStepData(): ColoringMethod {
        return coloringMethodChoiceHelper.coloringMethod
    }

    override fun isStepDataValid(stepData: ColoringMethod?): IsDataValid {
        val isValid = stepData != null
        return IsDataValid(isValid, if (isValid) {"Coloring method not chosen"} else {""})
    }

    override fun getStepDataAsHumanReadableString(): String {
        return stepData.toString()
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
        if (data != null)
            coloringMethodChoiceHelper.coloringMethod = data
    }
}