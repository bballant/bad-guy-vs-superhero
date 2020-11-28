package bb

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
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import javafx.stage.Stage

fun getCool() : String = "Cool"

val MAX_GUESSES = 12;

//fun main(args: Array<String>) {
//    println(superhero)
//    var line: String? = null;
//    var gameState = GameState("cool", emptySet(), "")
//    while({ line = readLine(); line}() != null) {
//        gameState = GameState(gameState.word, gameState.guesses - 1, gameState.guessedCorrectly)
//        println(line);
//        val guess = line?.first();
//        if (guess == null) {
//            println("Whoah No");
//        } else if (guess.toString() in gameState.word) {
//            println ("Yeah")
//        }
//        println("Game State: = " + gameState.toString());
//        printGameState(gameState);
//    }
//}

data class GameState(val word: String, val guessed: Set<Char>, val lastMessage: String);

fun doGuess(gameState: GameState, guess: Char): GameState {
    if (guess in gameState.guessed) {
        return gameState.copy(lastMessage = "You already tried that, fool!")
    } else {
        val recordedGuesses = gameState.guessed.plus(guess)
        val targetLetters = gameState.word.toSet()
        return if (guess in targetLetters) {
            val gs = GameState(gameState.word, recordedGuesses, "You got one!")
            if (targetLetters in recordedGuesses)
                gs.copy(lastMessage = "You won!")
            else gs
        } else {
            val wrongGuesses = (recordedGuesses subtract gameState.word.toSet())
            if (wrongGuesses.size >= MAX_GUESSES) {
                GameState(gameState.word, recordedGuesses, "Game Over!")
            } else {
                GameState(gameState.word, recordedGuesses, "Nuts, try again")
            }
        }
    }
}

val superhero = "─=≡Σ(([ ⊐•̀⌂•́]⊐"

// fun printGameState(gameState: GameState) {
//     println("==================================");
//     println(superhero.take(superhero.length - gameState.guesses));
//     println("==================================");
// }
//
// fun printGameState2(gameState: GameState) {
//     val f = {(_, gg, _): GameState -> gg}
//     f(gameState)
//     val (_, gs, _) = gameState;
//     println("==================================");
//     println(superhero.take(superhero.length - gs));
//     println("==================================");
// }

fun showGameState(stage: Stage, gameState: GameState) {

    val titleLabel = Label("╰(◣﹏◢)╯ Bad Guy! ╰(◣﹏◢)╯")
    titleLabel.font = Font.font("DejaVu Sans", FontWeight.BOLD, 30.0)
    titleLabel.padding = Insets(10.0)
    titleLabel.style = "-fx-background-color: Pink"
    titleLabel.alignment = Pos.CENTER

    val remGuessesPre = Label("You have")
    remGuessesPre.font = Font.font("DejaVu Sans", FontWeight.BOLD, 20.0)
    remGuessesPre.alignment = Pos.CENTER
    remGuessesPre.minWidth = 360.0

    val remGuessesPost = Label("guesses left!")
    remGuessesPost.font = Font.font("DejaVu Sans", FontWeight.BOLD, 20.0)
    remGuessesPost.alignment = Pos.CENTER
    remGuessesPost.minWidth = 360.0

    val wrongGuesses = (gameState.guessed subtract gameState.word.toSet())
    val remGuesses = MAX_GUESSES - wrongGuesses.size
    val remGuessesLabel = Label("" + remGuesses + "!")
    remGuessesLabel.font = Font.font("DejaVu Sans", FontWeight.EXTRA_BOLD, 50.0)
    remGuessesLabel.style = "-fx-background-color: LightGoldenRodYellow"
    remGuessesLabel.minWidth = 360.0
    remGuessesLabel.minHeight = 130.0
    remGuessesLabel.alignment = Pos.CENTER

    val guessesVBox = VBox(remGuessesPre, remGuessesLabel, remGuessesPost)

    val guessedLabel = Label(gameState.guessed.joinToString())
    guessedLabel.wrapTextProperty().value = true
    guessedLabel.font = Font.font("DejaVu Sans", FontWeight.BOLD, 30.0)
    guessedLabel.alignment = Pos.CENTER

    val message = Label(gameState.lastMessage)
    message.wrapTextProperty().value = true
    message.font = Font.font("DejaVu Sans Mono", FontWeight.EXTRA_BOLD, 20.0)
    message.alignment = Pos.CENTER

    val wordOut = gameState.word.toCharArray().fold("", { a, c ->
        if (c in gameState.guessed) a + c + " "
        else a + "_ "
    });

    val wordLabel = Label(wordOut)
    wordLabel.wrapTextProperty().value = true
    wordLabel.font = Font.font("DejaVu Sans Mono", FontWeight.EXTRA_BOLD, 40.0)
    wordLabel.style = "-fx-background-color: LightGoldenRodYellow"
    wordLabel.alignment = Pos.CENTER

    val guessText = TextField()
    guessText.maxWidth = 50.0
    val guessButton = Button("Guess!")
    guessButton.minWidth = 50.0
    guessButton.onAction = EventHandler {
        val letterGuess = guessText.text.first()
        val newState = doGuess(gameState, letterGuess)
        showGameState(stage, newState)
    }

    val scoreBox = HBox(guessesVBox, guessedLabel)
    val guessBox = HBox(guessText, guessButton)
    guessBox.padding = Insets(10.0, 10.0, 10.0, 10.0)
    guessBox.alignment = Pos.CENTER
    guessBox.spacing = 100.0
    val vbox = VBox(titleLabel, scoreBox, wordLabel, message, guessBox)

    vbox.alignment = Pos.CENTER
    vbox.padding = Insets(20.0, 20.0, 20.0, 20.0)
    vbox.spacing = 10.0

    val scene = Scene(vbox, 600.0, 400.0)
    stage.scene = scene
    stage.show()
}
