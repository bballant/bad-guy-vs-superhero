package bb

import javafx.application.Application
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.GridPane
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import javafx.stage.Stage

class HelloFX : Application() {

    override fun start(stage: Stage) {

        val grid = GridPane()
        grid.alignment = Pos.CENTER
        grid.hgap = 10.0
        grid.vgap = 10.0
        grid.padding = Insets(25.0, 25.0, 25.0, 25.0)

        val scene = Scene(grid, 600.0, 400.0)
        val theWord = "Foobar"

        val scenetitle = Text("Are you " + getCool() + "?")
        scenetitle.font = Font.font("Ubuntu Regular", FontWeight.NORMAL, 20.0)
        grid.add(scenetitle, 0, 0, theWord.length, 1)

        val userTextField = TextField()
        grid.add(userTextField, 0, 1, theWord.length, 1)

        for (i in 0 until theWord.length) {
            val s = theWord[i].toString()
            val tf = Text(s)
            tf.font = Font.font("Ubuntu Regular", FontWeight.EXTRA_BOLD, 40.0)
            grid.add(tf, i, 2)
        }

        for (i in 0 until theWord.length) {
            val tf = Text("-")
            tf.font = Font.font("Ubuntu Regular", FontWeight.EXTRA_BOLD, 40.0)
            grid.add(tf, i, 3)
        }

        val leButton = Button("Click!")
        grid.add(leButton, 1, 4, theWord.length - 1, 1)
        leButton.onAction = EventHandler {
            if (leButton.text === "Click!") {
                leButton.text = "Dang!"
            } else {
                leButton.text = "Click!"
            }
        }

        scene.addEventHandler(KeyEvent.KEY_PRESSED) { key: KeyEvent ->
            if (key.code == KeyCode.ENTER) {
                println("You pressed enter")
            }
        }
        stage.scene = scene
        stage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(bb.HelloFX::class.java)
        }
    }
}