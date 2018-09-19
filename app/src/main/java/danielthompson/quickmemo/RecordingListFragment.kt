package danielthompson.quickmemo

import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.melnykov.fab.FloatingActionButton
import java.io.File
import java.io.IOException
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.LinearLayoutManager


class RecordingListFragment : Fragment() {
    private val TAG: String = "RecordingListFragment"
    private val RECORDING_PREFIX = "Recording"
    val STORAGE_DIRECTORY = Environment.getExternalStorageDirectory().absolutePath + "/"

    private lateinit var mediaRecorder: MediaRecorder
    private lateinit var mediaFilePath: String
    private var isRecording: Boolean = false

    lateinit var recordButton: FloatingActionButton
    lateinit var adapter: RecordingListAdapter
    lateinit var presenter: RecordingListPresenter
    lateinit var list: RecyclerView

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root: View = inflater.inflate(R.layout.fragment_recording_list, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ButterKnife.bind(this, view)
        Log.e(TAG,"DMT: Fragment created")

        recordButton = view.findViewById(R.id.recordButton) as FloatingActionButton
        list = view.findViewById(R.id.recordingList)
        recordButton.setOnClickListener{toggleRecording()}

        list.layoutManager = LinearLayoutManager(context)

        presenter = RecordingListPresenter()
        presenter.populateRecordings()

        adapter = RecordingListAdapter(this)
        adapter.setRecordings(presenter.getRecordings())
        list.adapter = adapter

    }

    fun toggleRecording() {
        val fileName: String = RECORDING_PREFIX + Integer.toString(adapter.itemCount + 1) + ".3gp"
        mediaFilePath = STORAGE_DIRECTORY + fileName


        val fileout = File(mediaFilePath)
        if (fileout.exists()) {
            fileout.delete()
        }

        if (isRecording) {
            try {
                mediaRecorder.stop()
                val newItem = RecordingItem(mediaFilePath, fileName)
            } catch(e: RuntimeException) {
                fileout.delete()
                Log.e(TAG, "Something went wrong in stopping")
            } finally {
                mediaRecorder.release()
                isRecording = false
                Log.e(TAG, "Recording stopped")
            }

        } else {
            if (ActivityCompat.checkSelfPermission(context!!, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity!!, Array(1){ android.Manifest.permission.RECORD_AUDIO },
                        0)
            } else if(ActivityCompat.checkSelfPermission(context!!, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity!!, Array(1){ android.Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0)
            } else {
                startRecording()
            }
        }
    }

    fun startRecording() {
        mediaRecorder = MediaRecorder()
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB)
        mediaRecorder.setOutputFile(mediaFilePath)

        try {
            mediaRecorder.prepare()
        } catch (e: IOException) {
            Log.e(TAG, "prepare failed")
            isRecording = false
        }
        mediaRecorder.start()
        isRecording = true
        Log.e(TAG, "Recording started")
    }

}