import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SudokuSolverGUI extends JFrame {
    private JTextField[][] cells;
    private JButton solveButton;

    public SudokuSolverGUI() {
        setTitle("Sudoku Solver");
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a panel for the Sudoku grid
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(9, 9));

        cells = new JTextField[9][9];

        // Initialize the grid with text fields
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                cells[row][col].setFont(new Font("Arial", Font.PLAIN, 20));
                cells[row][col].setPreferredSize(new Dimension(40, 40));
                gridPanel.add(cells[row][col]);
            }
        }

        add(gridPanel, BorderLayout.CENTER);

        // Create a panel for the Solve button
        JPanel buttonPanel = new JPanel();
        solveButton = new JButton("Solve");
        buttonPanel.add(solveButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add an action listener for the Solve button
        solveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[][] board = new int[9][9];

                // Read user input into a 2D array
                for (int row = 0; row < 9; row++) {
                    for (int col = 0; col < 9; col++) {
                        String text = cells[row][col].getText();
                        if (!text.isEmpty()) {
                            board[row][col] = Integer.parseInt(text);
                        } else {
                            board[row][col] = 0;
                        }
                    }
                }

                // Solve the Sudoku puzzle
                if (solveSudoku(board)) {
                    // Display the solved Sudoku board
                    for (int row = 0; row < 9; row++) {
                        for (int col = 0; col < 9; col++) {
                            cells[row][col].setText(String.valueOf(board[row][col]));
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No solution found!");
                }
            }
        });
    }

    // Sudoku solver using backtracking
    private boolean solveSudoku(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isSafe(board, row, col, num)) {
                            board[row][col] = num;
                            if (solveSudoku(board)) {
                                return true;
                            }
                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    // Check if a number can be placed in the given cell
    private boolean isSafe(int[][] board, int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }

        int boxStartRow = row - row % 3;
        int boxStartCol = col - col % 3;
        for (int i = boxStartRow; i < boxStartRow + 3; i++) {
            for (int j = boxStartCol; j < boxStartCol + 3; j++) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SudokuSolverGUI().setVisible(true);
            }
        });
    }
}
