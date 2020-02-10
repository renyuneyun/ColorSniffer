package ryey.colorsniffer

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.part_color_method.*
import kotlinx.android.synthetic.main.part_color_preview.*
import ryey.colorsniffer.part.ColoringMethodChoiceHelper
import ryey.colorsniffer.part.PreviewViewHelper


class MainActivity : AppCompatActivity() {

    private lateinit var previewViewHelper: PreviewViewHelper
    private lateinit var coloringMethodChoiceHelper: ColoringMethodChoiceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        coloringMethodChoiceHelper =
            ColoringMethodChoiceHelper(radioGroup) { coloringMethod ->
                previewViewHelper.coloringMethod = coloringMethod
            }
        previewViewHelper =
            PreviewViewHelper(recyclerView)

        button_to_clipboard.setOnClickListener {
            val text = appListToTSV(previewViewHelper.getColoringInfo())
            putToClipboard(text)
        }

        button_form.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
        }

    }

    private fun putToClipboard(text: String) {
        val clipboardManager: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(LABEL_CLIPBOARD_DATA, text)
        clipboardManager.setPrimaryClip(clipData)
    }

    companion object {
        const val LABEL_CLIPBOARD_DATA = "app_color_list"

        fun appListToTSV(launcherActivityInfos: List<LauncherActivityInfo>): String {
            val buf = StringBuilder()
            for (app in launcherActivityInfos) {
                buf.append("%s\t#%X\n".format(app.packageName, ColoringMethod.color(app, ColoringMethod.dominantColor)))
            }
            return buf.toString()
        }
    }
}
