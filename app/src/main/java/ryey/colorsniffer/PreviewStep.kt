package ryey.colorsniffer

import android.view.LayoutInflater
import android.view.View
import ernestoyaquello.com.verticalstepperform.Step
import ryey.colorsniffer.part.PreviewViewHelper

class PreviewStep(
    private val defaultColorStep: DefaultColorStep,
    private val coloringMethodStep: ColoringMethodStep
) : Step<Unit>("Preview coloring") {

    val coloringMethod
        get() = coloringMethodStep.stepData
    val defaultColor
        get() = defaultColorStep.stepData

    lateinit var previewViewHelper: PreviewViewHelper
        private set

    override fun restoreStepData(data: Unit?) {
        previewViewHelper.coloringMethod = coloringMethodStep.stepData
    }

    override fun isStepDataValid(stepData: Unit?): IsDataValid {
        return IsDataValid(true)
    }

    override fun onStepMarkedAsCompleted(animated: Boolean) {
    }

    override fun getStepDataAsHumanReadableString(): String {
        return ""
    }

    override fun createStepContentLayout(): View {
        val view = LayoutInflater.from(context).inflate(R.layout.part_color_preview, null)

        previewViewHelper = PreviewViewHelper(
            view.findViewById(R.id.recyclerView),
            true,
            defaultColor,
            coloringMethod
        )

        return view
    }

    override fun getStepData() {
    }

    override fun onStepOpened(animated: Boolean) {
        previewViewHelper.defaultColor = defaultColor
        previewViewHelper.coloringMethod = coloringMethod
    }

    override fun onStepMarkedAsUncompleted(animated: Boolean) {
    }

    override fun onStepClosed(animated: Boolean) {
    }
}