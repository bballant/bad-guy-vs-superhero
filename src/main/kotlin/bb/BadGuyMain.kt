package bb

import javafx.application.Application
import javafx.stage.Stage
import java.io.File

class BadGuyMain : Application() {

    override fun start(stage: Stage) {
        val wordsFile =
            if (parameters.raw.isEmpty()) {
                File(javaClass.getResource("/word_list").toURI())
            } else File(parameters.raw.first())
        val scene = titleScene(stage,
            initialGameState(wordsFile.readLines().shuffled()))
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