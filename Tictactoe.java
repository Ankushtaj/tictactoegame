import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Tictactoe {
    static boolean gameEnd = false;
    static char currentPlayer;
    static int gameMode = 1;
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tic-Tac-Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLayout(new GridLayout(3, 3));

        String options[] = {"Player vs Player", "Player vs Computer"};
        int gameModeSelection = JOptionPane.showOptionDialog(frame, "Choose game mode", "Game Mode", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        gameMode = gameModeSelection + 1; 
        currentPlayer = 'X';
        gameEnd = false;

        JButton buttons[][] = new JButton[3][3];
        char board[][] = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '*';
                buttons[i][j] = new JButton("*");
                int row = i;
                int col = j;

                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleButtonClick(row, col, board, buttons);
                    }
                });
                frame.add(buttons[i][j]);
            }
        }
        frame.setVisible(true);
    }
    static void handleButtonClick(int row, int col, char[][] board, JButton[][] buttons) {
        if (board[row][col] == '*' && !gameEnd) {
            board[row][col] = currentPlayer;
            buttons[row][col].setText(String.valueOf(currentPlayer));
            gameEnd = gameWin(board, currentPlayer);
            if (gameEnd) {
                JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
            }
            else if(isBoardFull(board)){
                JOptionPane.showMessageDialog(null, "Game Draw!");
                gameEnd = true;
            }else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                if (gameMode == 2 && currentPlayer == 'O') {
                    computerMove(board, buttons);
                }
            }
        }
    }
    static boolean gameWin(char[][] board, char p) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == p && board[i][1] == p && board[i][2] == p) 
            return true;
            if (board[0][i] == p && board[1][i] == p && board[2][i] == p) 
            return true;
        }
        if (board[0][0] == p && board[1][1] == p && board[2][2] == p) 
        return true;
        if (board[0][2] == p && board[1][1] == p && board[2][0] == p) 
        return true;
        return false;
    }
    static void computerMove(char[][] board, JButton[][] buttons) {
        if (gameEnd) return; 
        int bestMove[] = findBestMove(board);
        if (bestMove[0] == -1 || bestMove[1] == -1) {
            return;
        }
        
        board[bestMove[0]][bestMove[1]] = 'O';
        buttons[bestMove[0]][bestMove[1]].setText("O");

        gameEnd = gameWin(board, 'O');
        if (gameEnd) {
            JOptionPane.showMessageDialog(null, "Computer wins!");
        } else if (isBoardFull(board)) {
            JOptionPane.showMessageDialog(null, "Game Draw!");
            gameEnd = true;
        } else {
            currentPlayer = 'X';
        }
    }
    static int[] findBestMove(char[][] board) {
        // Trying to win or block immediately
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '*') {
                    board[i][j] = 'O';
                    if (gameWin(board, 'O')) 
                    return new int[]{i, j};
                    board[i][j] = '*';
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '*') {
                    board[i][j] = 'X';
                    if (gameWin(board, 'X')) 
                    return new int[]{i, j};
                    board[i][j] = '*';
                }
            }
        }
        // If no winning or blocking move, picking a random empty spot
        int r,c;
        do{
            r=(int)(Math.random() * 3);
            c=(int)(Math.random() * 3);
        }while(board[r][c]!='*');
        if (board[r][c] == '*') 
        return new int[]{r, c};
        else
        return new int[]{-1, -1};
        
    }
    static boolean isBoardFull(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '*') {
                    return false;
                }
            }
        }
        return true;
    }
}


