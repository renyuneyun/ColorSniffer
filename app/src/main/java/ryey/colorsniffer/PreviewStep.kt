package ryey.colorsniffer

import android.view.LayoutInflater
import android.view.View
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import ernestoyaquello.com.verticalstepperform.Step
import kotlin.math.roundToInt

class PreviewStep(private val coloringMethodStep: ColoringMethodStep) : Step<Unit>("Preview coloring") {

    lateinit var adapter: MyAdapter
        private set

    override fun restoreStepData(data: Unit?) {
        adapter.coloringMethod = coloringMethodStep.stepData
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
        adapter = MyAdapter(context, coloringMethod)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.updateLayoutParams {
            height = context.resources.displayMetrics.let {
                (RECYCLER_VIEW_HEIGHT_IN_DP * it.density).roundToInt()
            }
        } //FIXME: temporary workaround. See https://github.com/ernestoyaquello/VerticalStepperForm/issues/88
        recyclerView.adapter = adapter

        return view
    }

    override fun getStepData() {
    }

    override fun onStepOpened(animated: Boolean) {
        adapter.coloringMethod = coloringMethodStep.stepData
    }

    override fun onStepMarkedAsUncompleted(animated: Boolean) {
    }

    override fun onStepClosed(animated: Boolean) {
    }

    companion object {
        const val RECYCLER_VIEW_HEIGHT_IN_DP = 400
    }
}