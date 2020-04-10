package tictactoe;
import java.util.Objects;
import java.util.Scanner;

/**
 * This project is a part of the Java Developer course on https://hyperskill.org.
 * Tic-Tac-Toe, also known as Noughts and crosses or Xs and Os.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        char[][] symbols = { {' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '} };

        // coordinate
        int x = 0;
        int y = 0;

        char player = 'X';

        do {
            // show field
            showField(symbols);

            do {
                System.out.print("Enter the coordinates: ");
                try {
                    x = scanner.nextInt();
                    y = scanner.nextInt();
                } catch (Exception e) {
                    System.out.println("You should enter numbers!");
                    scanner.nextLine();
                }

                if (x < 1 || x > 3 || y < 1 || y > 3) {
                    System.out.println("Coordinates should be from 1 to 3!");
                } else if (symbols[(y == 2 ? 1 : y == 1 ? 2 : 0) % 3][x - 1] == 'X'
                        || symbols[(y == 2 ? 1 : y == 1 ? 2 : 0) % 3][x - 1] == 'O') {
                    System.out.println("This cell is occupied! Choose another one!");
                } else {
                    break;
                }
            } while (true);

            // input recent move
            symbols[(y == 2 ? 1 : y == 1 ? 2 : 0) % 3][x - 1] = player;

            switch (Objects.requireNonNull(getState(symbols))) {
                case IMPOSSIBLE:
                    showField(symbols);
                    System.out.println("Impossible");
                    break;
                case DRAW:
                    showField(symbols);
                    System.out.println("Draw");
                    break;
                case O_WINS:
                    showField(symbols);
                    System.out.println("O wins");
                    break;
                case X_WINS:
                    showField(symbols);
                    System.out.println("X wins");
                default:
                    break;

            }
            // changing player
            player = player == 'X' ? 'O' : 'X';
        } while(gameNotFinished(symbols));
    }

    /**
     * Show the game field
     * @param symbols elements of the field
     */
    public static void showField(char[][] symbols) {
        System.out.println("---------");
        for (char[] symbol : symbols) {
            System.out.println(String.format("| %c %c %c |", symbol[0], symbol[1], symbol[2]));
        }
        System.out.println("---------");
    }

    /**
     * Check the state game
     * @param symbols elements of the field
     * @return a state
     */
    public static State getState(char[][] symbols) {

        if (isImpossible(symbols)) {
            return State.IMPOSSIBLE;
        } else if (gameNotFinished(symbols)) {
            return State.GAME_NOT_FINISHED;
        } else if (isDraw(symbols)) {
            return State.DRAW;
        } else if (isXWins(symbols)) {
            return State.X_WINS;
        } else if (isOWins(symbols)) {
            return (State.O_WINS);
        }
        return null;
    }

    /**
     * Check when no side has a three in a row but the field has empty cells
     * @param symbols elements of the field
     * @return true if the game is not finished otherwise return false
     */
    public static boolean gameNotFinished(char[][] symbols) {
        if (isXWins(symbols) || isOWins(symbols)) {
            return false;
        }

        for (char[] symbol : symbols) {
            for (int j = 0; j < symbols.length; j++) {
                if (symbol[j] == ' ' || symbol[j] == '_') {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check when no side has a three in a row and the field has no empty cells
     * @param symbols elements of the field
     * @return true if it is draw otherwise return false
     */
    public static boolean isDraw(char[][] symbols) {
        if (isXWins(symbols) || isOWins(symbols)) {
            return false;
        }

        for (char[] symbol : symbols) {
            for (int j = 0; j < symbols.length; j++) {
                if (symbol[j] == ' ' || symbol[j] == '_') {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check when the field has three 'element' in a row
     * @param symbols elements of the field
     * @param symbol the element to finding
     * @return true if elements wins otherwise return false
     */
    public static boolean isWins(char[][] symbols, char symbol) {

        for (int i = 0; i < symbols.length; i++) {
            // rows
            if (symbols[i][0] == symbol && symbols[i][1] == symbol && symbols[i][2] == symbol) { return true; }
            // cols
            if (symbols[0][i] == symbol && symbols[1][i] == symbol && symbols[2][i] == symbol) { return true; }
        }
        // diagonals
        if (symbols[0][0] == symbol && symbols[1][1] == symbol && symbols[2][2] == symbol) { return true; }
        return symbols[0][2] == symbol && symbols[1][1] == symbol && symbols[2][0] == symbol;
    }

    /**
     * Check when the field has three 'X' in a row
     * @param symbols elements of the field
     * @return true if X wins otherwise return false
     */
    public static boolean isXWins(char[][] symbols) {
        return isWins(symbols, 'X');
    }

    /**
     * Check when the field has three 'O' in a row
     * @param symbols elements of the field
     * @return true if O wins otherwise return false
     */
    public static boolean isOWins(char[][] symbols) {
        return isWins(symbols, 'O');
    }

    /**
     * Check when the field has three X in a row as well as three O in a row. Or the field has a lot more X's
     * that O's or vice versa (if the difference is 2 or more, should be 1 or 0)
     * @param symbols elements of the field
     * @return true if it is impossible otherwise return false
     */
    public static boolean isImpossible(char[][] symbols) {
        // three X in a row as well as three O in a row.
        if (isXWins(symbols) && isOWins(symbols)) {
            return true;
        }

        // more X's that O's or vice versa
        int countX = 0;
        int countO = 0;
        for (char[] symbol : symbols) {
            for (int j = 0; j < symbols.length; j++) {
                if (symbol[j] == 'X') {
                    countX++;
                } else if (symbol[j] == 'O') {
                    countO++;
                }
            }
        }
        return Math.abs(countX - countO) > 1;
    }
}