import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;
/**
 * @author Jacob Collins
 * @version 1.3
 * Main Menu so player can choose the size of the board
 */
public class MainMenuDialog {

    private ButtonType four, five;
    private int boardSize = 0;

    /**
     * Constructor for Main Menu
     */
    public MainMenuDialog()
    {

        //easier to use alert then to make anothe gui
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Main menu");
        alert.setHeaderText("Welcome to Boggle");
        alert.setContentText("What size board would you like?");

        //buttons for board size options
        four = new ButtonType("4 x 4");
        five = new ButtonType("5 x 5");

        alert.getButtonTypes().setAll(four, five);

        //checking to see what button was pressed and changing boardsize accordingly
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == four)
        {
            this.boardSize = 4;
        }
        else if (result.get() == five)
        {
            this.boardSize = 5;
        }

    }

    /**
     * Getter for board size to be used in Boggle GUI class
     *
     * @return boardSize
     */
    public int getSize()
    {
        return this.boardSize;
    }

}
