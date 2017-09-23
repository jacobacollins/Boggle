
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {

    private Random random;
    private char[][] boardArray;
    private int charnumber;
    private int percentage;
    private  String str;
    private boolean [][] visited;

    public Board(int boardSize){
        random = new Random();
        boardArray = new char[boardSize][boardSize];
        for(int i = 0; i < boardArray.length; i++){
            for(int j = 0; j < boardArray.length; j++){
                boardArray[i][j] = charGenerator();
            }
        }
        visited = new boolean[boardSize][boardSize];
    }



    public List<String> findAllWords(char[][] board, ArrayList<String> dictionary) {
        ArrayList<String> result = new ArrayList<String>();


        for (int i = 0; i < dictionary.size(); i++) {
            boolean isWord = false;
            for (int j = 0; j < boardArray.length; j++) {
                for (int k = 0; k < boardArray.length; k++) {

                    if (depthFirstSearch(board, dictionary.get(i), j, k, 0) ) {
                        isWord = true;
                    }
                }
            }
            if (isWord) {
                result.add(dictionary.get(i));
            }
        }

        return result;
    }

    public boolean depthFirstSearch(char[][] board, String word, int row, int col, int k) {


        if (row < 0 || col < 0 || row >= board.length || col >= board.length || k > word.length() - 1) {
            return false;
        }


        if (board[row][col] == word.charAt(k)) {
            char temp = board[row][col];
            visited[row][col] = true;
          // board[row][col] = '#';

            if (k == word.length() - 1) {

                return true;
            } else if (
                    //checks going vertically upwards
                    depthFirstSearch(board, word, row - 1, col, k + 1)
                    //checks going vertically downwards
                    || depthFirstSearch(board, word, row + 1, col, k + 1)
                    //checks going horizontally leftwards
                    || depthFirstSearch(board, word, row, col - 1, k + 1)
                    //checks going horizontally rightwards
                    || depthFirstSearch(board, word, row, col + 1, k + 1)
                    //makes sure that the cell isn't already visited
                    && !visited[row][col]
                    ) {
                board[row][col] = temp;

                return true;

            }

        } else {
            visited[row][col] = false;
            return false;
        }

        return false;
    }




    public ArrayList<String> readLines(String file, ArrayList<String> dictionary) throws IOException
    {
        FileReader fileReader = new FileReader(file);

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line = null;

        while ((line = bufferedReader.readLine()) != null)
        {
            dictionary.add(line);
        }

        bufferedReader.close();

        return dictionary;
    }

    public char getBoardChar(int i, int j) {
        return boardArray[i][j];
    }
    public char[][] getBoardArray() {
        return boardArray;
    }

    //statistics taken from this website
// https://www.math.cornell.edu/~mec/2003-2004/cryptography/subs/frequencies.html
    private char charGenerator() {

        charnumber = random.nextInt(100000);
        percentage = charnumber/8500;
        if(percentage < 0.07){
            return 'z';
        } else if(percentage < 0.10){
            return 'j';
        } else if(percentage < 0.11){
            return 'q';
        } else if(percentage < 0.17){
            return 'x';
        } else if(percentage < 0.69){
            return 'k';
        } else if(percentage < 1.11){
            return 'v';
        } else if(percentage < 1.49){
            return 'b';
        } else if(percentage < 1.82){
            return 'p';
        } else if(percentage < 2.03){
            return 'g';
        } else if(percentage < 2.09){
            return 'w';
        } else if(percentage < 2.11){
            return 'y';
        } else if(percentage < 2.30){
            return 'f';
        } else if(percentage < 2.61){
            return 'm';
        } else if(percentage < 2.71){
            return 'c';
        } else if(percentage < 2.88){
            return 'u';
        } else if(percentage < 3.98){
            return 'l';
        } else if(percentage < 4.32){
            return 'd';
        } else if(percentage < 5.92){
            return 'h';
        } else if(percentage < 6.02){
            return 'r';
        } else if(percentage < 6.28){
            return 's';
        } else if(percentage < 6.95){
            return 'n';
        } else if(percentage < 7.31){
            return 'i';
        } else if(percentage < 7.68){
            return 'o';
        } else if(percentage < 8.12){
            return 'a';
        } else if(percentage < 9.02){
            return 't';
        } else {
            return 'e';
        }
        }

}
