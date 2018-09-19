package danielthompson.quickmemo

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class RecordingViewholder(itemView: View, activity: RecordingListActivity) : RecyclerView.ViewHolder(itemView) {

    var activity: RecordingListActivity

    init {
        this.activity = activity
    }

    fun bind(item: RecordingItem) {
        val view: TextView = activity.findViewById<View>(R.id.recordingTitle) as TextView
        view.text = item.fileName
    }
}