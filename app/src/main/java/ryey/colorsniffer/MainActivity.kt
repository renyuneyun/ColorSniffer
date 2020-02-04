package ryey.colorsniffer

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = MyAdapter(this)

        recyclerView.adapter = adapter

        button_to_clipboard.setOnClickListener {
            adapter.waitForFinish()
            val text = appListToTSV(adapter.items)
            putToClipboard(text)
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
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
