package ryey.colorsniffer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ernestoyaquello.com.verticalstepperform.VerticalStepperFormView
import ernestoyaquello.com.verticalstepperform.listener.StepperFormListener

class FormFragment : Fragment(R.layout.fragment_form), StepperFormListener {

    private val defaultColorStep = DefaultColorStep()
    private val coloringMethodStep = ColoringMethodStep()
    private val previewStep = PreviewStep(coloringMethodStep)

    var resultListener: ResultListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_form, container)

        // Find the form view, set it up and initialize it.
        val verticalStepperForm = view.findViewById<VerticalStepperFormView>(R.id.stepper_form)
        verticalStepperForm
            .setup(this, defaultColorStep, coloringMethodStep, previewStep)
            .includeConfirmationStep(false)
            .displayCancelButtonInLastStep(true)
            .init()

        return view
    }

    override fun onCompletedForm() {
        resultListener?.onCompleted(defaultColorStep.stepData, coloringMethodStep.stepData, previewStep.previewViewHelper.getColoringInfo())
    }

    override fun onCancelledForm() {
        resultListener?.onCancelled()
    }

    interface ResultListener {
        fun onCompleted(
            defaultColor: Int,
            coloringMethod: ColoringMethod,
            launcherActivityInfoList: List<LauncherActivityInfo>
        )
        fun onCancelled()
    }

}