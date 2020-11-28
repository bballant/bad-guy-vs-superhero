package bb

import javafx.application.Application
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import javafx.stage.Stage

class BadGuyMain : Application() {

    val wordList = listOf(
            "SKATEBOARD",
            "CAUGHT",
            "MASCOT",
            "TAUGHT",
            "SAW",
            "MIMIC",
            "HOW",
            "DISRUPT",
            "AGAINST",
            "KNEW",
            "KNOW"
    )

    override fun start(stage: Stage) {

        val grid = GridPane()
        grid.alignment = Pos.CENTER
        grid.hgap = 10.0
        grid.vgap = 10.0
        grid.padding = Insets(25.0, 25.0, 25.0, 25.0)

        val scene = Scene(grid, 600.0, 400.0)

        val loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
        val loremSomething = "Something lorem something ipsum something dolor sit amet, ca blah blah blah blah I could go on but it is too boring."

        val label1 = Label(loremIpsum)
        label1.wrapTextProperty().value = true
        label1.style = "-fx-background-color: LightGoldenRodYellow"

        val label2 = Label(loremSomething)
        label2.wrapTextProperty().value = true
        label2.style = "-fx-background-color: LightGoldenRodYellow"

        val label = Label(loremIpsum)
        label.wrapTextProperty().value = true
        label.style = "-fx-background-color: LightGoldenRodYellow"

        val label3 = Label(loremIpsum)
        label3.wrapTextProperty().value = true
        label3.style = "-fx-background-color: LightGoldenRodYellow"

        grid.add(label1, 0, 0, 15, 1) // IMG
        grid.add(label2, 15, 0, 5, 1) // Guesses
        grid.add(label, 0, 1, 20, 1) // Word
        grid.add(label3, 0, 2, 20, 1) // Guess Box

        val gameState0 = GameState(wordList.shuffled().take(1)[0], emptySet(), "")

        scene.addEventHandler(KeyEvent.KEY_PRESSED) { key: KeyEvent ->
            if (key.code == KeyCode.ENTER) {
                showGameState(stage, gameState0)
            }
        }

        stage.scene = scene
        stage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(bb.BadGuyMain::class.java)
        }
    }
}