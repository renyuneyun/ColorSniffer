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


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = MyAdapter(this)

        recyclerView.adapter = adapter

        button_to_clipboard.setOnClickListener {
            adapter.waitForFinish()
            val text = appListToTSV(adapter.items)
            putToClipboard(text)
        }

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButton_dominate -> {
                    adapter.coloringMethod = ColoringMethod.dominantColor
                }
                R.id.radioButton_random -> {
                    adapter.coloringMethod = ColoringMethod.random
                }
                R.id.radioButton_vibrant -> {
                    adapter.coloringMethod = ColoringMethod.vibrantColor
                }
                else -> {
                    throw IllegalStateException("Unknown RadioButton for coloring method")
                }
            }
        }

        button_form.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
        }

    }

    fun putToClipboard(text: String) {
        val clipboardManager: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(LABEL_CLIPBOARD_DATA, text)
        clipboardManager.setPrimaryClip(clipData)
    }

    companion object {
        const val LABEL_CLIPBOARD_DATA = "app_color_list"

        fun appListToTSV(launcherActivityInfos: List<LauncherActivityInfo>): String {
            val buf = StringBuilder()
            for (app in launcherActivityInfos) {
                buf.append("%s\t%X\n".format(app.name, ColoringMethod.color(app, ColoringMethod.dominantColor)))
            }
            return buf.toString()
        }
    }
}
