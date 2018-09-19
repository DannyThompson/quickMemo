package danielthompson.quickmemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class RecordingListActivity : AppCompatActivity() {
    lateinit var fragment: RecordingListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recording_list)

        fragment = supportFragmentManager.findFragmentById(R.id.recordingListFragment) as RecordingListFragment
    }
}