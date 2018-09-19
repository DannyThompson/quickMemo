package danielthompson.quickmemo
import android.os.Environment
import java.io.File
import kotlin.collections.arrayListOf

class RecordingListPresenter() {
    val STORAGE_DIRECTORY = Environment.getExternalStorageDirectory().absolutePath + "/quickMemo/"

    private var recordings = arrayListOf<RecordingItem>()

    fun populateRecordings() {
        val files = File(STORAGE_DIRECTORY)
        val listFiles = files.listFiles()
        if(listFiles != null) {
            for (file: File in listFiles) {
                val item = RecordingItem(STORAGE_DIRECTORY, file.name)
                recordings.add(item)
            }
        }
    }

    fun getRecordings(): ArrayList<RecordingItem> {
        return recordings
    }

}