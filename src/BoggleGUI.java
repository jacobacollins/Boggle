import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class BoggleGUI extends Application {


    private int boardSize, tileX, tileY, scoreCounter, time;
    private ArrayList<String> dictionary;
    private ArrayList<String> correctAnswers, rightGuesses, wrongGuesses;
    private boolean[][] clicked;
    private StringBuilder string = new StringBuilder();
    private Button[][] boardButtons;
    private Board boggle;
    private Label guesses, timer, score;
    private BorderPane root;
    private VBox leftPane;
    private Button add, clear;
    private HBox rightPane;
    private TextArea correct, incorrect;
    private GridPane center;
    private FlowPane bottom;
    private TextField entry;


    /**
     * blank constructor
     */
    public BoggleGUI()
    {


    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {

        tileX = -1;
        tileY = -1;
        dictionary = new ArrayList<String>();

        //main menu so player can choose the size of the board
        MainMenuDialog menu = new MainMenuDialog();
        boardSize = menu.getSize();


        //intializing back end components and the correct answers
        scoreCounter = 0;
        clicked = new boolean[boardSize][boardSize];
        boggle = new Board(boardSize);
        boggle.readLines(dictionary);
        correctAnswers = new ArrayList<String>(boggle.findAllWords(boggle.getBoardArray(),  dictionary));
        System.out.println(dictionary);


        rightGuesses = new ArrayList<String>();
        wrongGuesses = new ArrayList<String>();

        root = new BorderPane();
        root.setStyle("-fx-background-color:darkgoldenrod");


        //everything to be added to the left portion of the gui
        leftPane = new VBox();
        leftPane.setAlignment(Pos.CENTER);
        leftPane.setSpacing(50);
        leftPane.setPadding(new Insets(0, 20, 0, 20));

        add = new Button("Add Word");
        add.setPrefSize(75, 30);

        clear = new Button("Clear");
        clear.setPrefSize(75, 30);

        timer = new Label("");
        timerLabel(180000, primaryStage);
        timer.setFont(Font.font(20));
        timer.setAlignment(Pos.CENTER);

        leftPane.getChildren().add(timer);
        leftPane.getChildren().add(add);
        leftPane.getChildren().add(clear);
        root.setLeft(leftPane);


        //everything to be added to the right portion of the gui
        rightPane = new HBox();
        rightPane.setSpacing(20);
        rightPane.setAlignment(Pos.CENTER);
        rightPane.setPadding(new Insets(27, 20, 27, 20));

        correct = new TextArea("Correct Words");
        correct.setEditable(false);
        correct.setPrefSize(95, 400);

        incorrect = new TextArea("Incorrect Words");
        incorrect.setEditable(false);
        incorrect.setStyle("-fx-text-inner-color:red;");
        incorrect.setPrefSize(105, 400);


        rightPane.getChildren().add(correct);
        rightPane.getChildren().add(incorrect);
        root.setRight(rightPane);


        //everything to be added to the center portion of the gui
        center = new GridPane();
        center.setAlignment(Pos.CENTER);
        root.setCenter(center);

        boardButtons = new Button[boardSize][boardSize];
        makeBoardButtons();


        //everything to be added to the bottom portion of the gui
        bottom = new FlowPane();
        entry = new TextField();
        entry.setPromptText("Enter your guesses here");
        entry.setAlignment(Pos.BASELINE_LEFT);

        score = new Label("Score: 0");
        score.setFont(Font.font(20));

        guesses = new Label("Guessed word");
        guesses.setFont(Font.font(20));

        bottom.getChildren().addAll(entry, guesses, score);
        bottom.setAlignment(Pos.CENTER);
        bottom.setHgap(40);
        bottom.setVgap(20);
        root.setBottom(bottom);


        //adding various listeners
       // addButtonListener();
       // clearButtonListener();
        textEntryListener();

        //prepping the stage
        primaryStage.setTitle("Boggle");
        primaryStage.setScene(new Scene(root, 675, 300));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * This takes the backend components of the grid and assigns a button to each value
     */
    private void makeBoardButtons()
    {
        for (int i = 0; i < boardSize; i++)
        {
            for (int j = 0; j < boardSize; j++)
            {
                boardButtons[i][j] = new Button(String.valueOf(boggle.getBoardChar(i, j)));

                boardButtons[i][j].setPrefSize(45, 45);
                boardButtons[i][j].setStyle("-fx-background-color: wheat; " +
                        "-fx-border-color: black;" +
                        "-fx-padding: 5 5 5 5");
             //   boardButtonListener(boardButtons[i][j]);

                center.add(boardButtons[i][j], i, j);
            }
        }
    }

    /**
     * This resets our tile positions and then resets our string builder and then enables all buttons on
     * the board
     */
    private void clearGuess()
    {
        tileX = -1;
        tileY = -1;

        string.setLength(0);
        for (int i = 0; i < boardSize * boardSize; i++)
        {

            center.getChildren().get(i).setDisable(false);

            center.getChildren().get(i).setStyle("-fx-background-color: wheat; " +
                    "-fx-border-color: black;" +
                    "-fx-padding: 5 5 5 5");
        }
    }

    /**
     * This is our add button listener that will inform us of valid/invalid words
     * as well as update the score.
     */
    private void addButtonListener() {
        add.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if (event.getSource().equals(add))
                {

                    if (correctAnswers.contains(string.toString()))
                    {
                        correct.appendText("\n" + string);
                        guesses.setText(string.toString() + " is a valid word");
                        rightGuesses.add(string.toString());
                        if (string.toString().length() > 0)
                        {
                            scoreCounter += (string.toString().length() - 2) * 100;
                        }
                        score.setText("Score: " + scoreCounter);

                    }
                    else
                        {
                        incorrect.appendText("\n" + string);
                        guesses.setText(string.toString() + " is not a valid word");
                        wrongGuesses.add(string.toString());

                    }
                    clearGuess();
                }
            }
        });
    }

    /**
     * This is the button listener that is assigned to our boggle board. It will only allow button clicks in the
     * available perimeter of Button b.
     *
     * @param b The button that is clicked
     */
    private void boardButtonListener(Button b)
    {
        b.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if (event.getSource().equals(b))
                {


                    for (int i = 0; i < boardSize; i++)
                    {
                        for (int j = 0; j < boardSize; j++)
                        {
                            if (event.getSource() == boardButtons[i][j])
                            {

                                if ((tileX != -1 && tileY != -1) && (Math.abs(tileX - i) <= 1 && Math.abs(tileY - j) <= 1)
                                        || (tileX == -1 && tileY == -1))
                                {

                                    string.append(boggle.getBoardChar(i, j));
                                    guesses.setText(string.toString());

                                    tileX = i;
                                    tileY = j;
                                    clicked[i][j] = true;

                                    boardButtons[i][j].setDisable(true);
                                }
                                else
                                {

                                    break;
                                }
                            }
                        }
                    }

                }


            }

        });

    }

    /**
     * Clear the current word that is being guessed
     */
    private void clearButtonListener()
    {

        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                clearGuess();
            }
        });

    }

    /**
     * This handles words that are guessed from the text field
     */
    private void textEntryListener()
    {
        entry.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if (entry.getText() != null
                        && event.getSource() == entry
                        && !entry.getText().isEmpty())
                {

                    if (dictionary.contains(entry.getText()))
                    {

                        guesses.setText(entry.getText() + " is a valid word");
                        rightGuesses.add(entry.getText());
                        correct.appendText("\n" + entry.getText());
                        if (entry.getText().length() > 0)
                        {
                            scoreCounter += (entry.getText().length() - 2) * 100;
                        }
                        entry.setText("");
                        score.setText("Score: " + scoreCounter);
                    }
                    else
                    {

                        guesses.setText(entry.getText() + " is not a valid word");
                        wrongGuesses.add(entry.getText());
                        incorrect.appendText("\n" + entry.getText());
                        entry.setText("");
                    }
                }
            }
        });

    }

    /**
     * The timer will run based on milliseconds passed in, conversion will be done, and when it reaches zero we will display our end game screen.
     *
     * @param mil    The milliseconds from which the timer will start
     * @param parent The parent stage, passed in so we can tie it to the end Game dialog when the timer runs out
     */
    private void timerLabel(int mil, Stage parent)
    {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        time = mil;

        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        new EventHandler<ActionEvent>()
                        {
                            @Override

                            public void handle(ActionEvent event)
                            {


                                timer.setAlignment(Pos.TOP_CENTER);
                                timer.setAlignment(Pos.CENTER);

                                timer.setText(
                                        "  Time Left\n" +
                                                time / 1000 / 60 + " min " + ((time / 1000) % 60) + " sec");

                                timer.setAlignment(Pos.CENTER);
                                if (time > 0) {
                                    time -= 1000;
                                }
                                else
                                {
                                    timeline.stop();
                                    System.out.println("Done");
                                    EndGameDialog end = new EndGameDialog(parent, scoreCounter, rightGuesses, wrongGuesses, correctAnswers);
                                }


                            }
                        })
        );
        timeline.playFromStart();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}







