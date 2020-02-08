package ryey.colorsniffer

import android.view.LayoutInflater
import android.view.View
import ernestoyaquello.com.verticalstepperform.Step
import ryey.colorsniffer.part.DefaultColorHelper

class DefaultColorStep : Step<Int>("Default color") {

    lateinit var defaultColorHelper: DefaultColorHelper

    override fun restoreStepData(data: Int?) {
        if (data != null) {
            defaultColorHelper.defaultColor = data
        }
    }

    override fun isStepDataValid(stepData: Int?): IsDataValid {
        return IsDataValid(stepData != null)
    }

    override fun onStepMarkedAsCompleted(animated: Boolean) {
    }

    override fun getStepDataAsHumanReadableString(): String {
        return "0x%X".format(defaultColorHelper.defaultColor)
    }

    override fun createStepContentLayout(): View {
        val view = LayoutInflater.from(context).inflate(R.layout.part_default_color, null)

        defaultColorHelper = DefaultColorHelper(view.findViewById(R.id.imageView_default_color))

        return view
    }

    override fun getStepData(): Int {
        return defaultColorHelper.defaultColor
    }

    override fun onStepOpened(animated: Boolean) {
    }

    override fun onStepMarkedAsUncompleted(animated: Boolean) {
    }

    override fun onStepClosed(animated: Boolean) {
    }
}