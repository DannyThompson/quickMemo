package danielthompson.quickmemo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.io.File

class RecordingListAdapter(fragment: RecordingListFragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var fragment: RecordingListFragment

    private var recordings = arrayListOf<RecordingItem>()

    init {
        this.fragment = fragment
    }

    fun setRecordings(recordings: ArrayList<RecordingItem>) {
        this.recordings = recordings
    }

    override fun getItemCount(): Int {
        return recordings.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RecordingViewholder).bind(recordings.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recording_item, parent, false)
        return RecordingViewholder(view, fragment.activity as RecordingListActivity)
    }

}