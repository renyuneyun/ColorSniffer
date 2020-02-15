package ryey.colorsniffer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class FormActivity : AppCompatActivity(), FormFragment.ResultListener, FormFragment.InitialDataSource {

    private var initialColoring: ColoringResult.Partial? = null

    override fun onAttachFragment(fragment: Fragment) {
        if (fragment is FormFragment) {
            fragment.resultListener = this
            fragment.initialDataSource = this
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialColoring = intent.extras?.let {
            ColoringResult.Partial.fromExtras(it)
        }
        setContentView(R.layout.activity_form)
    }

    override fun onCompleted(coloringResult: ColoringResult) {
        Toast.makeText(this, "Form completed with coloring method <%s>".format(coloringResult.coloringMethod), Toast.LENGTH_LONG).show()
        val intent = Intent()
        intent.putExtras(coloringResult.asIPCBundle())
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onCancelled() {
        Toast.makeText(this, "Form cancelled", Toast.LENGTH_LONG).show()
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    override fun get(): ColoringResult.Partial? {
        return initialColoring
    }
}
