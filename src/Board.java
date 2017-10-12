
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {

        private static final String file = "src/OpenEnglishWordList.txt";
    private Random random;
    private char[][] boardArray;
    private int charnumber;
    private int percentage;
    private int charFrequency = 0;


    private boolean[][] visited;

    /**
     * Constructor for back end board grid
     * @param boardSize The size of the board we would like to create
     */
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


    /**
     * Finds all valid words given the dictionary arrayList
     * @param board char array to check words for
     * @param dictionary what the char array is checked against
     * @return returns the valid words found on the board.
     */
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

    /**
     * Depth First search that will check for valid words on the bogggle board.
     * @param board char array representing the board
     * @param word the string to be checked for validity
     * @param row the row to be checked
     * @param col the column to be checked
     * @param wordSize the size of the word
     * @return true or false based on the validity of a word
     */
    public boolean depthFirstSearch(char[][] board, String word, int row, int col, int wordSize) {


        if (row < 0 || col < 0 || row >= board.length || col >= board.length || wordSize > word.length() - 1) {
            return false;
        }


        if (board[row][col] == word.charAt(wordSize)) {
            char temp = board[row][col];
            visited[row][col] = true;
            // board[row][col] = '#';

            if (wordSize == word.length() - 1) {

                return true;
            } else if (
                //checks going vertically upwards
                    depthFirstSearch(board, word, row - 1, col, wordSize + 1)
                            //checks going vertically downwards
                            || depthFirstSearch(board, word, row + 1, col, wordSize + 1)
                            //checks going horizontally leftwards
                            || depthFirstSearch(board, word, row, col - 1, wordSize + 1)
                            //checks going horizontally rightwards
                            || depthFirstSearch(board, word, row, col + 1, wordSize + 1)
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


    /**
     * Read lines will check the txt file provided as the dictionary and put it into an ArrayList
     * @param dictionary ArrayList that will hold the dictionary
     * @return The dictionary ArrayList
     * @throws IOException
     */

    public ArrayList<String> readLines(ArrayList<String> dictionary) throws IOException
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


    /**
     * Returns array position i , j so that we can access the char from that position
     *
     * @param i the row coordinate
     * @param j the column coordinate
     * @return Array position
     */
    public char getBoardChar(int i, int j) {
        return boardArray[i][j];
    }


    /**
     * Utility method that returns the boardArray
     *
     * @return boardArray returns whole array
     */
    public char[][] getBoardArray() {
        return boardArray;
    }


    /**
     * This is a utility function to check and see if a letter appears more than 4 times in an array
     *
     * @param c Char to be checked for
     * @return Returns true or false based on the frequency of chars in the array.
     */
    private boolean charFrequency(char c) {
        for (int j = 0; j < boardArray.length; j++) {
            for (int k = 0; k < boardArray.length; k++) {
                if (boardArray[j][k] == c) {
                    ++charFrequency;
                } else if (charFrequency == 4) {
                    return true;
                }
            }
        }
        charFrequency = 0;
        return false;
    }
    //statistics taken from this website
// https://www.math.cornell.edu/~mec/2003-2004/cryptography/subs/frequencies.html

    /**
     * This will generate the chars for the board.
     *
     * @return char The char that will be used in the board array
     */
    private char charGenerator()
    {

        charnumber = random.nextInt(100000);
        percentage = charnumber / 8500;
        if (percentage < 0.07)
        {
            if (charFrequency('z'))
            {
                return charGenerator();
            }
            else
            {
                return 'z';
            }
        }
        else if (percentage < 0.10)
        {
            if (charFrequency('j'))
            {
                return charGenerator();
            }
            else
            {
                return 'j';
            }
        }
        else if (percentage < 0.11)
        {
            if (charFrequency('q'))
            {
                return charGenerator();
            }
            else
            {
                return 'q';
            }
        }
        else if (percentage < 0.17)
        {
            if (charFrequency('x'))
            {
                return charGenerator();
            }
            else
            {
                return 'x';
            }
        }
        else if (percentage < 0.69)
        {
            if (charFrequency('k'))
            {
                return charGenerator();
            }
            else
            {
                return 'k';
            }
        }
        else if (percentage < 1.11)
        {
            if (charFrequency('v'))
            {
                return charGenerator();
            }
            else
            {
                return 'v';
            }
        }
        else if (percentage < 1.49)
        {
            if (charFrequency('b'))
            {
                return charGenerator();
            }
            else
            {
                return 'b';
            }
        }
        else if (percentage < 1.82)
        {
            if (charFrequency('p'))
            {
                return charGenerator();
            }
            else
            {
                return 'p';
            }
        }
        else if (percentage < 2.03)
        {
            if (charFrequency('g'))
            {
                return charGenerator();
            }
            else
            {
                return 'g';
            }
        }
        else if (percentage < 2.09)
        {
            if (charFrequency('w'))
            {
                return charGenerator();
            }
            else
            {
                return 'w';
            }
        }
        else if (percentage < 2.11)
        {
            if (charFrequency('y'))
            {
                return charGenerator();
            }
            else
            {
                return 'y';
            }
        }
        else if (percentage < 2.30)
        {
            if (charFrequency('f'))
            {
                return charGenerator();
            }
            else
            {
                return 'f';
            }
        }
        else if (percentage < 2.61)
        {
            if (charFrequency('m'))
            {
                return charGenerator();
            }
            else
            {
                return 'm';
            }
        }
        else if (percentage < 2.71)
        {
            if (charFrequency('c'))
            {
                return charGenerator();
            }
            else
            {
                return 'c';
            }
        }
        else if (percentage < 2.88)
        {
            if (charFrequency('u'))
            {
                return charGenerator();
            }
            else
            {
                return 'u';
            }
        }
        else if (percentage < 3.98)
        {
            if (charFrequency('l'))
            {
                return charGenerator();
            }
            else
            {
                return 'l';
            }
        }
        else if (percentage < 4.32)
        {
            if (charFrequency('d'))
            {
                return charGenerator();
            }
            else
            {
                return 'd';
            }
        }
        else if (percentage < 5.92)
        {
            if (charFrequency('h'))
            {
                return charGenerator();
            }
            else
            {
                return 'h';
            }
        }
        else if (percentage < 6.02)
        {
            if (charFrequency('r'))
            {
                return charGenerator();
            }
            else
            {
                return 'r';
            }
        }
        else if (percentage < 6.28)
        {
            if (charFrequency('s'))
            {
                return charGenerator();
            }
            else
            {
                return 's';
            }
        }
        else if (percentage < 6.95)
        {
            if (charFrequency('n'))
            {
                return charGenerator();
            }
            else
            {
                return 'n';
            }
        }
        else if (percentage < 7.31)
        {
            if (charFrequency('i'))
            {
                return charGenerator();
            }
            else
            {
                return 'i';
            }
        }
        else if (percentage < 7.68)
        {
            if (charFrequency('o'))
            {
                return charGenerator();
            }
            else
            {
                return 'o';
            }
        }
        else if (percentage < 8.12)
        {
            if (charFrequency('a'))
            {
                return charGenerator();
            }
            else
            {
                return 'a';
            }
        } else if (percentage < 9.02) {
            if (charFrequency('t')) {
                return charGenerator();
            } else {
                return 't';
            }
        } else {
            if (charFrequency('e')) {
                return charGenerator();
            } else {
                return 'e';
            }
        }
    }

}
