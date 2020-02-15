package ryey.colorsniffer.part

import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import ryey.colorsniffer.ColoringMethod
import kotlin.math.roundToInt

class PreviewViewHelper(private val recyclerView: RecyclerView, coloringMethod: ColoringMethod = ColoringMethod.DEFAULT, fixedHeight: Boolean = false) {

    var coloringMethod = coloringMethod
        set(value) {
            field = value
            adapter.coloringMethod = coloringMethod
        }
    var adapter: PreviewAdapter

    init {
        recyclerView.context.let { context ->
            adapter = PreviewAdapter(context, coloringMethod)
            if (fixedHeight) {
                recyclerView.updateLayoutParams {
                    height = context.resources.displayMetrics.let {
                        (RECYCLER_VIEW_HEIGHT_IN_DP * it.density).roundToInt()
                    }
                } //FIXME: temporary workaround. See https://github.com/ernestoyaquello/VerticalStepperForm/issues/88
            }
            recyclerView.adapter = adapter
        }
    }

    fun getColoringInfo(): List<LauncherActivityInfo> {
        adapter.waitForFinish()
        return adapter.items
    }

    companion object {
        const val RECYCLER_VIEW_HEIGHT_IN_DP = 400
    }
}