

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class Main extends Application {

    //change this to change the size of the board
    private static int boardSize = 5;
    private ArrayList<String> dictionary = new ArrayList<String>();
    private ArrayList<String> correctAnswers;
    private int blah = 180;



    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color:gray");

        Board boggle = new Board(boardSize);

        VBox leftPane = new VBox();
        leftPane.setAlignment(Pos.CENTER);
        leftPane.setSpacing(65);
        leftPane.setPadding(new Insets(0, 20, 0, 20));

        Button add = new Button("Add Word");
        add.setPrefSize(75,30);
        Button clear = new Button("Clear");
        clear.setPrefSize(75,30);
        Label timer = new Label("");
        timer.setFont(Font.font(20));
        timer.setAlignment(Pos.CENTER);

        leftPane.getChildren().add(timer);
        leftPane.getChildren().add(add);
        leftPane.getChildren().add(clear);

        root.setLeft(leftPane);

        HBox rightPane = new HBox();
        rightPane.setSpacing(20);
        rightPane.setAlignment(Pos.CENTER);
        rightPane.setPadding(new Insets(27,20,27,20));
        TextArea correct = new TextArea("Correct Words");
        correct.setPrefSize(95, 400);

        TextArea incorrect = new TextArea("Incorrect Words");
        incorrect.setPrefSize(105,400);
        rightPane.getChildren().add(correct);
        rightPane.getChildren().add(incorrect);

        root.setRight(rightPane);



       Timer timer1 = new Timer(timer, 180000);




        GridPane center = new GridPane();
        root.setCenter(center);

        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
               Label label =  new Label(String.valueOf(boggle.getBoardChar(i, j)));
               label.setFont(Font.font(20));


               Button b = new Button(String.valueOf(boggle.getBoardChar(i,j)));
                b.setPrefSize(45,45);
                b.setStyle("-fx-background-color: wheat; " +
                           "-fx-border-color: black;" +
                           "-fx-padding: 5 5 5 5");

                b.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if(event.getSource().equals(b)){
                            System.out.print(b.getText());
                           // b.setStyle("-fx-background-color:baby-blue");
                        }
                    }
                });

                center.add(b, i, j);



            }
        }

        center.setAlignment(Pos.CENTER);
        root.setCenter(center);


        FlowPane bottom = new FlowPane();
        TextField entry = new TextField("Enter your guesses here");
        entry.setAlignment(Pos.BASELINE_LEFT);

        Label guesses = new Label("Guessed word");
        guesses.setFont(Font.font(15));

        guesses.setAlignment(Pos.BASELINE_RIGHT);

       bottom.getChildren().addAll(entry, guesses);
        bottom.setHgap(40);
        bottom.setVgap(20);
        root.setBottom(bottom);


        entry.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(entry.getText() != null
                        && event.getSource() == entry
                        && !entry.getText().isEmpty()) {

                    if (dictionary.contains(entry.getText())) {

                        guesses.setText(entry.getText() + " is a valid word");
                        entry.setText("");
                    } else {

                        guesses.setText(entry.getText() + " is not a valid word");
                        entry.setText("");
                    }
                }
            }
        });



        boggle.readLines("src/OpenEnglishWordList.txt", dictionary);
       //correctAnswers = new ArrayList<String>(boggle.findAllWords(boggle.getBoardArray(),dictionary));
       //System.out.println(correctAnswers);







        primaryStage.setTitle("Boggle");
        primaryStage.setScene(new Scene(root, 675, 300));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
