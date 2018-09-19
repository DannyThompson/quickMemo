package danielthompson.quickmemo

class RecordingItem(absoluteFilePath: String, fileName: String) {
    var fileName : String
    var absoluteFilePath : String

     init {
         this.fileName = fileName
         this.absoluteFilePath = absoluteFilePath
     }
}