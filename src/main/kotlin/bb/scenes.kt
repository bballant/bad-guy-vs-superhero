package bb

import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import javafx.stage.Stage

data class WordState(val unseen: List<String>, val seen: List<String>)
data class GameState(val wordState: WordState, val word: String,
                     val guessed: Set<Char>, val lastMessage: String);

val MESSAGE_FONT = Font.font("DejaVu Sans", FontWeight.BOLD, 20.0)
val LETTER_FONT = Font.font("DejaVu Sans Mono", FontWeight.EXTRA_BOLD, 40.0)
val MAX_GUESSES = 12;

fun initialGameState(wordList:  List<String>): GameState {
    return GameState(WordState(wordList, emptyList()), wordList.first(), emptySet(), "")
}

fun makeSeen(wordState: WordState) : WordState {
    return if (wordState.unseen.isEmpty()) wordState
    else WordState(wordState.unseen.drop(1), wordState.seen + wordState.unseen.first())
}

fun resetGame(gameState: GameState): GameState {
    val wordState = makeSeen(gameState.wordState)
    return if (wordState.unseen.isEmpty()) {
        initialGameState(wordState.seen.shuffled())
    } else {
        GameState(wordState, wordState.unseen.first(), emptySet(), "")
    }
}

fun doGuess(gameState: GameState, guess: Char): GameState {
    return if (guess in gameState.guessed) {
        gameState.copy(lastMessage = "You already tried that, fool!")
    } else {
        val gs = gameState.copy(guessed = gameState.guessed.plus(guess))
        val targetLetters = gameState.word.toSet()
        if (guess in targetLetters) {
            if (gs.guessed.containsAll(targetLetters)) gs.copy(lastMessage = "You Won!")
            else gs.copy(lastMessage = "You got one!")
        } else {
            val wrongGuesses = (gs.guessed subtract gameState.word.toSet())
            if (wrongGuesses.size >= MAX_GUESSES) {
                gs.copy(lastMessage = "Game Over!")
            } else {
                gs.copy(lastMessage = "Nuts, try again")
            }
        }
    }
}

fun titleScene(stage: Stage, gameState: GameState): Scene {
    val img = ImageView("title.png")
    img.fitHeight = 560.0
    val label = Label("Press Enter To Continue")
    label.font = MESSAGE_FONT
    label.alignment = Pos.CENTER
    label.minWidth = 780.0
    val vbox = VBox(img, label)
    vbox.alignment = Pos.CENTER
    val scene = Scene(vbox, 800.0, 600.0)
    scene.addEventHandler(KeyEvent.KEY_PRESSED) { key: KeyEvent ->
        if (key.code == KeyCode.ENTER) {
            val newScene = getNextScene(stage, gameState)
            stage.scene = newScene
            stage.show()
        }
    }
    return scene
}

fun getYouLoseScene(stage: Stage, gameState: GameState): Scene {
    val img = ImageView("you_lose.png")
    img.fitHeight = 560.0
    val label = Label("The word was *" + gameState.word + "*!. Press Enter To Continue.")
    label.font = MESSAGE_FONT
    label.alignment = Pos.CENTER
    label.minWidth = 780.0
    val vbox = VBox(img, label)
    vbox.alignment = Pos.CENTER
    val scene = Scene(vbox, 800.0, 600.0)
    scene.addEventHandler(KeyEvent.KEY_PRESSED) { key: KeyEvent ->
        if (key.code == KeyCode.ENTER) {
            val newScene = titleScene(stage, resetGame(gameState))
            stage.scene = newScene
            stage.show()
        }
    }
    return scene
}

fun getYouWinScene(stage: Stage, gameState: GameState): Scene {
    val img = ImageView("you_win.png")
    img.fitHeight = 560.0
    val label = Label("The word was *" + gameState.word + "*! Press Enter To Continue.")
    label.font = MESSAGE_FONT
    label.alignment = Pos.CENTER
    label.minWidth = 780.0
    val vbox = VBox(img, label)
    vbox.alignment = Pos.CENTER
    val scene = Scene(vbox, 800.0, 600.0)
    scene.addEventHandler(KeyEvent.KEY_PRESSED) { key: KeyEvent ->
        if (key.code == KeyCode.ENTER) {
            val newScene = titleScene(stage, resetGame(gameState))
            stage.scene = newScene
            stage.show()
        }
    }
    return scene
}

fun getGameStateScene(stage: Stage, gameState: GameState): Scene {

    val titleLabel = Label("Bad Guy vs Superhero!")
    titleLabel.font = Font.font("DejaVu Sans", FontWeight.BOLD, 30.0)
    titleLabel.padding = Insets(10.0)
    titleLabel.alignment = Pos.CENTER

    val titleBadGuy = ImageView("bad_guy.png")
    titleBadGuy.fitHeight = 50.0
    titleBadGuy.fitWidth = 75.0

    val titleBadGuy2 = ImageView("hero.png")
    titleBadGuy2.fitHeight = 50.0
    titleBadGuy2.fitWidth = 75.0

    val titleBox = HBox(titleBadGuy, titleLabel, titleBadGuy2);
    titleBox.padding = Insets(10.0, 10.0, 10.0, 10.0)
    titleBox.alignment = Pos.CENTER
    titleBox.spacing = 10.0

    val wrongGuesses = (gameState.guessed subtract gameState.word.toSet())
    val remGuesses = MAX_GUESSES - wrongGuesses.size

    val remGuessesImage = ImageView("bad_guy.png")
    val baseImgHeight = 270.0
    val baseImgWidth = 360.0
    val leFitHeight = (baseImgHeight * (1 - (remGuesses / MAX_GUESSES.toDouble()))) + 1.0
    val leFitWidth = (baseImgWidth * (1 - (remGuesses / MAX_GUESSES.toDouble()))) + 1.0
    remGuessesImage.fitHeight = leFitHeight
    remGuessesImage.fitWidth = leFitWidth

    val remGuessesImageVBox = VBox(remGuessesImage)
    remGuessesImageVBox.minHeight = baseImgHeight
    remGuessesImageVBox.minWidth = baseImgWidth
    remGuessesImageVBox.alignment = Pos.CENTER

    val guessedLabel = Label(gameState.guessed.joinToString())
    guessedLabel.wrapTextProperty().value = true
    guessedLabel.font = Font.font("DejaVu Sans", FontWeight.BOLD, 30.0)
    guessedLabel.alignment = Pos.CENTER

    val remGuessesLabel = Label("You have " + remGuesses + " guesses left!")
    remGuessesLabel.font = MESSAGE_FONT
    remGuessesLabel.alignment = Pos.CENTER

    val message = Label(gameState.lastMessage)
    message.wrapTextProperty().value = true
    message.font = MESSAGE_FONT
    message.alignment = Pos.CENTER

    val wordOut = gameState.word.toCharArray().fold("", { a, c ->
        if (c in gameState.guessed) a + c + " "
        else a + "_ "
    });

    val wordLabel = Label(wordOut)
    wordLabel.wrapTextProperty().value = true
    wordLabel.font = LETTER_FONT
    wordLabel.style = "-fx-background-color: LightGoldenRodYellow"
    wordLabel.alignment = Pos.CENTER

    val guessHero = ImageView("hero.png")
    guessHero.fitHeight = 100.0
    guessHero.fitWidth = 150.0

    val guessText = TextField()
    guessText.maxWidth = 100.0
    guessText.font = LETTER_FONT
    guessText.onAction = EventHandler {
        val letterGuess = guessText.text.first()
        val newState = doGuess(gameState, letterGuess)
        val newScene = getNextScene(stage, newState)
        stage.scene = newScene
        stage.show()
    }

    val guessButton = Button("Guess!")
    guessButton.minWidth = 50.0
    guessButton.font = LETTER_FONT
    guessButton.minHeight = 30.0
    guessButton.onAction = EventHandler {
        val letterGuess = guessText.text.first()
        val newState = doGuess(gameState, letterGuess)
        val newScene = getNextScene(stage, newState)
        stage.scene = newScene
        stage.show()
    }

    val scoreBox = HBox(remGuessesImageVBox, guessedLabel)
    val guessBox = HBox(guessHero, guessText, guessButton)
    guessBox.padding = Insets(10.0, 10.0, 10.0, 10.0)
    guessBox.alignment = Pos.CENTER
    guessBox.spacing = 100.0
    val vbox = VBox(titleBox, scoreBox, remGuessesLabel,
        wordLabel, message, guessBox, Text(""))
    vbox.alignment = Pos.CENTER
    vbox.padding = Insets(20.0, 20.0, 20.0, 20.0)
    vbox.spacing = 10.0

    val scene = Scene(vbox, 800.0, 600.0)
    return scene
}

fun getNextScene(stage: Stage, gameState: GameState): Scene {
    return if (gameState.lastMessage == "You Won!") {
        getYouWinScene(stage, gameState)
    } else if (gameState.lastMessage == "Game Over!") {
        getYouLoseScene(stage, gameState)
    } else {
        getGameStateScene(stage, gameState)
    }
}
