package ryey.colorsniffer.part

import android.content.res.Resources
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import ryey.colorsniffer.ColoringMethod
import ryey.colorsniffer.ColoringResult
import kotlin.math.roundToInt

class PreviewViewHelper(
    private val recyclerView: RecyclerView,
    fixedHeight: Boolean = false,
    initialDefaultColor: Int = ResourcesCompat.getColor(
        Resources.getSystem(),
        android.R.color.black,
        null
    ),
    initialColoringMethod: ColoringMethod = ColoringMethod.DEFAULT
) {

    private var adapter: PreviewAdapter

    init {
        recyclerView.context.let { context ->
            adapter = PreviewAdapter(context, initialColoringMethod, initialDefaultColor)
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

    var coloringMethod
        get() = adapter.coloringMethod
        set(value) {adapter.coloringMethod = value}
    var defaultColor
        get() = adapter.defaultColor
        set(value) {adapter.defaultColor = value}

    fun getColoringResult(): ColoringResult {
        return ColoringResult(
            defaultColor,
            coloringMethod,
            adapter.run {
                waitForFinish()
                items
            }
        )
    }

    companion object {
        const val RECYCLER_VIEW_HEIGHT_IN_DP = 400
    }
}