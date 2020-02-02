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

    }

    fun putToClipboard(text: String) {
        val clipboardManager: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(LABEL_CLIPBOARD_DATA, text)
        clipboardManager.setPrimaryClip(clipData)
    }

    companion object {
        const val LABEL_CLIPBOARD_DATA = "app_color_list"

        fun appListToTSV(apps: List<App>): String {
            val buf = StringBuilder()
            for (app in apps) {
                buf.append("%s\t%X\n".format(app.name, app.dominantColor.color))
            }
            return buf.toString()
        }
    }
}
