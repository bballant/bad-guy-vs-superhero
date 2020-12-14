package bb

import javafx.application.Application
import javafx.stage.Stage
import java.io.File

class BadGuyMain : Application() {

    override fun start(stage: Stage) {
        val wordList =
            if (parameters.raw.isEmpty()) listOf("superhero")
            else File(parameters.raw.first()).readLines().shuffled()
        val scene = titleScene(stage, initialGameState(wordList))
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
