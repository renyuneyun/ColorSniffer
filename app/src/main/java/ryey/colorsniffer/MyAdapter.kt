package ryey.colorsniffer

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.ConditionVariable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference


class MyAdapter(val context: Context) : RecyclerView.Adapter<MyViewHolder>() {

    val items: ArrayList<App>
    var size: Int
        private set
    val loadAppTask: LoadAppTask

    init {
        val pm = context.packageManager
        val packages = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        size = packages.size
        items = ArrayList(size)
        loadAppTask = LoadAppTask(WeakReference(this))
        loadAppTask.execute(packages)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        while (loadAppTask.curr < position) {
            Thread.sleep(500)  // TODO: fine grained wait (e.g. use lock)
        }
        val app: App = items[position]
        holder.imageView.setImageDrawable(app.icon)
        holder.imageView2.setImageDrawable(app.dominantColor)
    }

    fun waitForFinish() {
        loadAppTask.waitForFinish()
    }

    class LoadAppTask(private val adapter: WeakReference<MyAdapter>) : AsyncTask<List<ApplicationInfo>, Drawable, Unit>() {

        var curr: Int = -1
            private set

        private val finished: ConditionVariable = ConditionVariable(false)

        override fun doInBackground(vararg packages: List<ApplicationInfo>) {
            assert(packages.size == 1)
            for (packageInfo in packages[0]) {
                adapter.get()?.let {
                    if (packageInfo.name == null) {
                    } else {
                        it.items.add(App(it.context, packageInfo))
                        curr++
                    }
                }
            }
            finished.open()
        }

        override fun onPostExecute(result: Unit?) {
            adapter.get()?.let {
                if (curr != it.size) {
                    it.size = curr
                    it.notifyDataSetChanged()
                }
            }
        }

        fun waitForFinish() {
            finished.block()
        }
    }
}