package ryey.colorsniffer

import android.view.LayoutInflater
import android.view.View
import ernestoyaquello.com.verticalstepperform.Step
import ryey.colorsniffer.part.PreviewViewHelper

class PreviewStep(private val coloringMethodStep: ColoringMethodStep) : Step<Unit>("Preview coloring") {

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

        val coloringMethod = coloringMethodStep.stepData
        previewViewHelper = PreviewViewHelper(
            view.findViewById(R.id.recyclerView),
            coloringMethod,
            true
        )

        return view
    }

    override fun getStepData() {
    }

    override fun onStepOpened(animated: Boolean) {
        previewViewHelper.coloringMethod = coloringMethodStep.stepData
    }

    override fun onStepMarkedAsUncompleted(animated: Boolean) {
    }

    override fun onStepClosed(animated: Boolean) {
    }
}