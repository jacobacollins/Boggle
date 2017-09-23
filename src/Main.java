

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
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

        /*
        Label timer = new Label("timer");
        timer.setFont(Font.font(20));
        timer.setAlignment(Pos.CENTER);
        root.setTop(timer);


       Timer timer1 = new Timer(timer, 180000);




        GridPane center = new GridPane();
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
               Label label =  new Label(String.valueOf(boggle.getBoardChar(i, j)));
               label.setFont(Font.font(20));
               center.setHgap(20);
               center.setVgap(5);
                center.add(label, i, j);

            }
        }

        center.setAlignment(Pos.CENTER);
        root.setCenter(center);
*/

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
        primaryStage.setScene(new Scene(root, 350, 300));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
