import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.util.ArrayList;


/**
 * @author Jacob Collins
 * @version 1.3
 * End Game Menu for Boggle Game
 */
public class EndGameDialog
{


    private Alert dialog = new Alert(Alert.AlertType.INFORMATION);

    /**
     * End Game Dialog Constructor
     * Displays score, words guessed correctly, incorrectly, and the list of valid words
     *
     * @param parent     parent stage to be closed when this window closes
     * @param score      final score
     * @param right      words guessed that were correct
     * @param wrong      words guessed that were wrong
     * @param allAnswers list of all valid words that would have been accepted
     */
    public EndGameDialog(Stage parent, int score, ArrayList<String> right, ArrayList<String> wrong, ArrayList<String> allAnswers)
    {

        dialog.setTitle("Game Over");
        dialog.setHeaderText("Your score is " + score);
        dialog.setContentText("The words you correctly guessed were \n\t" + right +
                "\n\n The words you guessed incorrectly were \n\t" + wrong +
                "\n\nA list of acceptable words would have been \n\t" + allAnswers);


        dialog.show();

        dialog.setOnHidden(evt -> parent.close());
    }
}


