import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import bb.*;

public class HelloFX extends Application {

    @Override
    public void start(Stage stage) {
        //String javaVersion = System.getProperty("java.version");
        //String javafxVersion = System.getProperty("javafx.version");
        //Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        //Scene scene = new Scene(new StackPane(l), 640, 480);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 600, 400);

        String theWord = "Foobar";

        Text scenetitle = new Text("Are you " + AppKt.getCool() + "?");
        scenetitle.setFont(Font.font("Ubuntu Regular", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, theWord.length(), 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 0, 1, theWord.length(), 1);

        for (int i = 0; i < theWord.length(); i ++) {
            String s = String.valueOf(theWord.charAt(i));
            Text tf = new Text(s);
            tf.setFont(Font.font("Ubuntu Regular", FontWeight.EXTRA_BOLD, 40));
            grid.add(tf, i, 2);
        }

        for (int i = 0; i < theWord.length(); i ++) {
            Text tf = new Text("-");
            tf.setFont(Font.font("Ubuntu Regular", FontWeight.EXTRA_BOLD, 40));
            grid.add(tf, i, 3);
        }

        Button leButton = new Button("Click!");
        grid.add(leButton, 1, 4, theWord.length() - 1, 1);

        leButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (leButton.getText() == "Click!") {
                    leButton.setText("Dang!");
                } else {
                    leButton.setText("Click!");
                }
            }
        });

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()== KeyCode.ENTER) {
                System.out.println("You pressed enter");
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}