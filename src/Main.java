import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    WordFindHandler wordFinder;
    {
        try {
            wordFinder = new WordFindHandler();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    Button button;
    TextField boardInput;
    TextField maxLength;
    TextField maxSize;
    TextArea ansText;

    Label resultText;
    BorderPane border;
    FlowPane configs;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        border = new BorderPane();

        primaryStage.setTitle("Word Finder (4x4 board)");
        primaryStage.setIconified(false);

        boardInput = new TextField();
        boardInput.setPromptText("Type board here...");
        boardInput.setMaxWidth(150);
        boardInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 16) {
                boardInput.setText(boardInput.getText().substring(0,16));
            }
        });

        maxLength = new TextField();
        maxLength.setText("10");
        maxLength.setMaxWidth(50);
        maxLength.setPromptText("Length");

        maxSize = new TextField();
        maxSize.setText("500");
        maxSize.setMaxWidth(50);
        maxSize.setPromptText("Size");

        button = new Button();
        button.setMaxWidth(250);
        button.setText("Search");
        button.setAlignment(Pos.BASELINE_RIGHT);
        button.setOnAction(event -> {
            setBoard();
            setBoundary();
            getResults();
        });

        resultText = new Label();
        resultText.setMaxWidth(100);
        resultText.setWrapText(true);

        ansText = new TextArea();
        ansText.setEditable(false);
        ansText.setWrapText(true);

        configs = new FlowPane();
        configs.setHgap(10);
        configs.getChildren().addAll(boardInput, maxLength, maxSize, button, resultText);

        border.setTop(configs);
        border.setCenter(ansText);

        Scene scene = new Scene(border, 500, 300);
        primaryStage.setMinHeight(150);
        primaryStage.setMinWidth(250);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    private void setBoard() {
        try {
            wordFinder.setBoard(String.valueOf(boardInput.getCharacters()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void setBoundary() {
        wordFinder.setMAX_LENGTH(Integer.parseInt(maxLength.getText()));
        wordFinder.setMAX_SIZE(Integer.parseInt(maxSize.getText()));
    }
    private void getResults() {
        wordFinder.search();

        int i = 0;
        String word = "";
        for (String w : wordFinder.getWords()) {
            word = word.concat(w + "\n");
            i++;
        }
        if (word.length() > 3)
            ansText.setText(word.substring(0,word.length()-1));
        else
            ansText.setText("");
        resultText.setText("Words Found: " + i);
    }
}