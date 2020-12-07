package bb

import javafx.application.Application
import javafx.stage.Stage
import java.io.File

class BadGuyMain : Application() {

    override fun start(stage: Stage) {
        val wordsFilePath =
            if (parameters.raw.isEmpty()) "words_file" // sample words in resources
            else parameters.raw.first()
        val scene = titleScene(stage, initialGameState(File(wordsFilePath).readLines().shuffled()))
        stage.scene = scene
        stage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(bb.BadGuyMain::class.java, *args)
        }
    }
}