package ryey.colorsniffer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class FormActivity : AppCompatActivity(), FormFragment.ResultListener {

    override fun onAttachFragment(fragment: Fragment) {
        if (fragment is FormFragment) {
            fragment.resultListener = this
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
    }

    override fun onCompleted(
        defaultColor: Int,
        coloringMethod: ColoringMethod,
        launcherActivityInfoList: List<LauncherActivityInfo>
    ) {
        Toast.makeText(this, "Form completed with coloring method <%s>".format(coloringMethod), Toast.LENGTH_LONG).show()
        val intent = Intent()
        val colorBundle = Bundle()
        colorBundle.putInt(KEY_DEFAULT_COLOR, defaultColor)
        for (launcherActivityInfo in launcherActivityInfoList) {
            colorBundle.putInt(launcherActivityInfo.packageName, ColoringMethod.color(launcherActivityInfo, coloringMethod))
        }
        intent.putExtra(KEY_COLOR_BUNDLE, colorBundle)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onCancelled() {
        Toast.makeText(this, "Form cancelled", Toast.LENGTH_LONG).show()
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    companion object {
        const val KEY_COLOR_BUNDLE = "color_bundle"
        const val KEY_DEFAULT_COLOR = "default_color_for_apps"
    }
}
