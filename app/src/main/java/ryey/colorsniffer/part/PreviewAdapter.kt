package ryey.colorsniffer.part

import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.ConditionVariable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ryey.colorsniffer.ColoringMethod
import ryey.colorsniffer.LauncherActivityInfo
import ryey.colorsniffer.R
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.ArrayList


class PreviewAdapter(val context: Context, coloringMethod: ColoringMethod = ColoringMethod.DEFAULT) : RecyclerView.Adapter<PreviewViewHolder>() {

    var coloringMethod: ColoringMethod = coloringMethod
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    val items: ArrayList<LauncherActivityInfo>
    private var size: Int
    private val loadAppTask: LoadAppTask

    init {
        val pm = context.packageManager

        val appList = Intent(Intent.ACTION_MAIN, null).let {
            it.addCategory(Intent.CATEGORY_LAUNCHER)
            val appList =
                pm.queryIntentActivities(it, 0)
            Collections.sort(appList, ResolveInfo.DisplayNameComparator(pm))
            appList
        }
        size = appList.size
        items = ArrayList(size)
        loadAppTask =
            LoadAppTask(WeakReference(this))
        loadAppTask.execute(appList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_preview, parent, false)
        return PreviewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return size
    }

    override fun onBindViewHolder(holder: PreviewViewHolder, position: Int) {
        while (loadAppTask.curr < position) {
            Thread.sleep(500)  // TODO: fine grained wait (e.g. use lock)
        }
        val launcherActivityInfo: LauncherActivityInfo = items[position]
        holder.fillWith(launcherActivityInfo, coloringMethod)
    }

    fun waitForFinish() {
        loadAppTask.waitForFinish()
    }

    class LoadAppTask(private val adapter: WeakReference<PreviewAdapter>) : AsyncTask<List<ResolveInfo>, Drawable, Unit>() {

        var curr: Int = -1
            private set

        private val finished: ConditionVariable = ConditionVariable(false)

        override fun doInBackground(vararg appList: List<ResolveInfo>) {
            assert(appList.size == 1)
            Log.d("BGLoad", "loading app list")
            val time1 = Date()
            for (app in appList[0]) {
                adapter.get()?.let {
                    it.items.add(
                        LauncherActivityInfo(
                            it.context,
                            app.activityInfo
                        )
                    )
                    curr++
                }
            }
            finished.open()
            val time2 = Date()
            Log.d("BGLoad", "loaded app list, %d ms elapsed".format(time2.time - time1.time))

        }

        fun waitForFinish() {
            finished.block()
        }
    }
}