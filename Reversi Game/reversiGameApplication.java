import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.border.*;

/**
 * Reversi is a two-player game in which the players place identical game pieces
 * called disks on
 * a rectangular board. Disks are light on one side and dark on the other. The
 * objective of the game is to have
 * the majority of disks turned to display the player's colour when the last
 * playable empty square is filled.
 *
 * @author (Jasmine Yamkum)
 * @version (April 2021)
 */
public class reversiGameApplication extends JFrame implements ActionListener {
    // instance fields
    private static JLabel title;
    private JFrame frame;

    private JPanel scoresBoard, board, topPanel;
    private JButton[][] button = new JButton[8][8];

    // discs to be show on buttons
    private ImageIcon clearCircle = new ImageIcon("clear-circle.png");
    private ImageIcon redCircle = new ImageIcon("red-circle.png");
    private ImageIcon greenCircle = new ImageIcon("green-circle.png");

    private JTextArea display = new JTextArea(1, 10);
    private JTextField[] scores = new JTextField[2];
    private JLabel players[] = new JLabel[2];

    private JLabel player1, player2;
    private String username1, username2;
    private JButton newGame = new JButton("New Game");
    int redScore = 0, greenScore = 0;

    private Color gameBoardColour = Color.lightGray;
    private Color changeColour;
    private boolean isRedPlayerTurn;

    /**
     * Create new game of Reversi and display/show it on screen
     */

    public class Coordinates {
        int x;
        int y;
    }

    Coordinates temporarySourcebtnAxis = new Coordinates();
    Coordinates tempBtnCoordinates = new Coordinates();

    /**
     * Inculdes title, starting new game and current player's turn
     */
    private void topPanel() {
        // creating labels which inculde title of game and scoresboard
        topPanel = new JPanel(new BorderLayout(8, 5));
        title = new JLabel();
        title.setText("REVERSI GAME ");
        title.setFont(new Font("Arial Black", Font.BOLD, 25));
        title.setForeground(Color.BLUE);
        topPanel.add(title, BorderLayout.LINE_START);

        // display board to show whose turn
        display.setEditable(false);
        display.setFont(new Font("Arial Black", Font.BOLD, 20));
        display.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        display.setPreferredSize(new Dimension(105, 45));
        topPanel.add(display, BorderLayout.CENTER);

        // new game button to be added to top of rightSide screen
        newGame.setEnabled(true);
        newGame.setForeground(Color.blue);
        newGame.setFont(new Font("Arial", Font.BOLD, 16));
        newGame.addActionListener(this);
        topPanel.add(newGame, BorderLayout.LINE_END);
    }

    /**
     * Adding and setting Player Panel components
     * Setting names of player1 and player2 and saving into new Panel
     */
    private void makePlayerPanel() {
        // creating scoresBoard/playerPanel
        scoresBoard = new JPanel();
        BoxLayout boxlayout = new BoxLayout(scoresBoard, BoxLayout.Y_AXIS);
        scoresBoard.setLayout(boxlayout);

        JPanel playerBoard1 = new JPanel();
        playerBoard1.setBackground(Color.white);
        JPanel playerBoard2 = new JPanel();
        playerBoard2.setBackground(Color.white);

        JLabel red = new JLabel("Red");
        red.setForeground(Color.red);
        JLabel green = new JLabel("Green");
        green.setForeground(Color.GREEN);
        player1 = new JLabel("Player 1: " + username1 + "  ");
        player2 = new JLabel("Player 2: " + username2);

        // creating playerboard and adding finish details to scoresboard
        for (int i = 0; i < 2; i++) {
            scores[i] = new JTextField();
            scores[i].setPreferredSize(new Dimension(75, 45));
            scores[i].setEditable(false);
            scores[i].setForeground(Color.black);
            scores[i].setFont(new Font("Arial Black", Font.BOLD, 20));
            scores[i].setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

            players[i] = new JLabel();
            players[i].setForeground(Color.black);
            players[i].setPreferredSize(new Dimension(75, 45));
            players[i].setVisible(true);
        }
        scores[0].setBackground(Color.red);
        scores[1].setBackground(Color.green);

        playerBoard1.add(red);
        playerBoard1.add(player1);
        playerBoard2.add(green);
        playerBoard2.add(player2);

        // add player's current details with spacing included
        scoresBoard.add(playerBoard1);
        scoresBoard.add(scores[0]);
        scoresBoard.add(Box.createVerticalStrut(10));
        scoresBoard.add(playerBoard2);
        scoresBoard.add(scores[1]);
    }

    // resizing discs to fit on buttons of gameBoard
    private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    /**
     * Display game board, grid will be filled with buttons
     */
    private void board() {
        board = new JPanel();
        board.setBackground(Color.gray);

        // setting board panel with grid layout of 8 rows, 8 columns
        board.setLayout(new GridLayout(8, 8));

        // creating 8x8 board using buttons to fill the grid
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                button[i][j] = new JButton();
                button[i][j].addActionListener(this);
                button[i][j].setBounds(0, 0, 0, 60);
                button[i][j].setForeground(Color.gray);
                button[i][j].setBackground(gameBoardColour);
                button[i][j].setBorder(BorderFactory.createEtchedBorder());
                board.add(button[i][j]);

            }
        }
    }

    /**
     * Creating the Swing Frame to implement the game and functionality
     */
    private void makeFrame() {
        frame = new JFrame("Reversi");
        username1 = JOptionPane.showInputDialog(null, "Enter Player 1 Name: ");
        username2 = JOptionPane.showInputDialog(null, "Enter Player 2 Name: ");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel contentPane = (JPanel) frame.getContentPane();
        contentPane.setBorder(new EmptyBorder(12, 12, 12, 12));

        frame.addWindowListener(new WindowAdapter() {
            // creating a subclass
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(frame,
                        "Are you sure you want to quit game? ",
                        "Quit confirmation",
                        JOptionPane.YES_NO_OPTION);
                if (choice == 0)
                    System.exit(0);
            }
        });

        // specify the layout manager with nice spacing
        contentPane.setLayout(new BorderLayout(6, 6));

        // display board component which is 8x8 board
        board();
        contentPane.add(board, BorderLayout.WEST);

        // display top section component of jframe
        topPanel();
        contentPane.add(topPanel, BorderLayout.NORTH);

        // display side playerPanel which includes the scoeBoard
        makePlayerPanel();
        contentPane.add(scoresBoard, BorderLayout.EAST);

        initialise();

        frame.pack();
        frame.setVisible(true);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(d.width / 2 - frame.getWidth() / 2, d.height / 2 - frame.getHeight() / 2);
    }

    /**
     * This function sets the initial conditions for the board
     */
    private void initialise() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                button[i][j].setEnabled(false);// All the buttons are disabled for now.

                button[i][j].setBackground(gameBoardColour);
                button[i][j].setForeground(Color.gray);
                int offset = button[3][3].getInsets().left;
                button[3][3].setIcon(
                        resizeIcon(clearCircle, button[3][3].getWidth() - offset, button[3][3].getHeight() - offset));
            }
        }
        isRedPlayerTurn = true;
        display.setText(" " + username1 + "'s turn");

        // setting the beginning 4 buttons to be able to use in first go
        button[3][3].setEnabled(true);
        button[4][4].setEnabled(true);
        button[3][4].setEnabled(true);
        button[4][3].setEnabled(true);

        int offset1 = button[3][3].getInsets().left;
        button[3][3]
                .setIcon(resizeIcon(redCircle, button[3][3].getWidth() - offset1, button[3][3].getHeight() - offset1));
        int offset2 = button[4][4].getInsets().left;
        button[4][4]
                .setIcon(resizeIcon(redCircle, button[4][4].getWidth() - offset2, button[4][4].getHeight() - offset2));
        int offset3 = button[3][4].getInsets().left;
        button[3][4].setIcon(
                resizeIcon(greenCircle, button[3][4].getWidth() - offset3, button[3][4].getHeight() - offset3));
        int offset4 = button[4][3].getInsets().left;
        button[4][3].setIcon(
                resizeIcon(greenCircle, button[4][3].getWidth() - offset4, button[4][3].getHeight() - offset4));

        button[3][3].setBackground(Color.red);
        button[4][4].setBackground(Color.red);
        button[3][4].setBackground(Color.green);
        button[4][3].setBackground(Color.green);

        showNextMoves(Color.red);
        board.setVisible(true);
    }

    /**
     * This shows next moves avaliable by enabling the button to be true ie.
     * clickable
     */
    public void showNextMoves(Color nextPlayerColour) {
        JButton nextAvaliableMove;
        Coordinates btnAxis = new Coordinates();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                nextAvaliableMove = button[i][j];
                btnAxis.x = i;
                btnAxis.y = j;

                int countOfNextMove = 0;

                if (nextAvaliableMove.getBackground().equals(gameBoardColour)) {
                    // Checking if any possible move exists
                    countOfNextMove = countMovesAvaliable(btnAxis, nextPlayerColour);

                    if (countOfNextMove == 0)
                        // i.e. no possible move for the given button
                        continue;
                    else {
                        nextAvaliableMove.setForeground(Color.black);
                        nextAvaliableMove.setEnabled(true);
                    }
                }
            }
        }
    }

    /**
     * To change the colour of the actual discs if other player has succeeded in
     * changing discs
     */
    public void changeColour(Coordinates btnAxis, Color currentPlayerColor) {
        if (isRedPlayerTurn)
            changeColour = Color.green;

        else
            changeColour = Color.red;

        Coordinates left = tempBtnCoordinates;
        // for converting pieces in left(West) of clicked Button
        left.x = btnAxis.x - 1;
        left.y = btnAxis.y;

        if ((left.x >= 0) && !(button[left.x][left.y].getBackground().equals(gameBoardColour))) {

            while ((left.x >= 0) && (button[left.x][left.y].getBackground().equals(changeColour)))
                left.x--;

            if ((left.x == -1) || (button[left.x][left.y].getBackground().equals(gameBoardColour))) {
                // do nothing
            }

            else
                for (int i = (left.x + 1); i <= btnAxis.x; i++)
                    button[i][left.y].setBackground(currentPlayerColor);
        }

        Coordinates rightSide = tempBtnCoordinates;
        // for converting pieces in rightSide(East) of clicked Button
        rightSide.x = btnAxis.x + 1;
        rightSide.y = btnAxis.y;

        if ((rightSide.x <= 7) && !(button[rightSide.x][rightSide.y].getBackground().equals(gameBoardColour))) {

            while ((rightSide.x <= 7) && (button[rightSide.x][rightSide.y].getBackground().equals(changeColour)))
                rightSide.x++;

            if ((rightSide.x == 8) || (button[rightSide.x][rightSide.y].getBackground().equals(gameBoardColour))) {
                // do nothing
            }

            else
                for (int i = (rightSide.x - 1); i >= btnAxis.x; i--)
                    button[i][rightSide.y].setBackground(currentPlayerColor);
        }

        Coordinates north = tempBtnCoordinates;
        // for converting pieces in north of clicked Button
        north.x = btnAxis.x;
        north.y = btnAxis.y + 1;

        if ((north.y <= 7) && !(button[north.x][north.y].getBackground().equals(gameBoardColour))) {

            while ((north.y <= 7) && (button[north.x][north.y].getBackground().equals(changeColour)))
                north.y++;

            if ((north.y == 8) || (button[north.x][north.y].getBackground().equals(gameBoardColour))) {
                // do nothing
            }

            else
                for (int i = (north.y - 1); i >= btnAxis.y; i--)
                    button[north.x][i].setBackground(currentPlayerColor);
        }

        Coordinates south = tempBtnCoordinates;
        // for converting pieces in south of clicked Button
        south.x = btnAxis.x;
        south.y = btnAxis.y - 1;

        if ((south.y >= 0) && !(button[south.x][south.y].getBackground().equals(gameBoardColour))) {

            while ((south.y >= 0) && (button[south.x][south.y].getBackground().equals(changeColour)))
                south.y--;

            if ((south.y == -1) || (button[south.x][south.y].getBackground().equals(gameBoardColour))) {
                // do nothing
            }

            else
                for (int i = (south.y + 1); i <= btnAxis.y; i++)
                    button[south.x][i].setBackground(currentPlayerColor);
        }

        Coordinates northWest = tempBtnCoordinates;
        // for converting pieces in northWest of clicked Button
        northWest.x = btnAxis.x - 1;
        northWest.y = btnAxis.y + 1;

        if ((northWest.x >= 0) && (northWest.y <= 7)
                && !(button[northWest.x][northWest.y].getBackground().equals(gameBoardColour))) {

            while ((northWest.x >= 0) && (northWest.y <= 7)
                    && (button[northWest.x][northWest.y].getBackground().equals(changeColour))) {
                northWest.y++;
                northWest.x--;
            }

            if ((northWest.x == -1) || (northWest.y == 8)
                    || (button[northWest.x][northWest.y].getBackground().equals(gameBoardColour))) {
                // do nothing
            }

            else
                for (int i = (northWest.y - 1); i >= btnAxis.y; i--) {
                    northWest.x++;
                    button[northWest.x][i].setBackground(currentPlayerColor);
                }
        }

        Coordinates northEast = tempBtnCoordinates;
        // for converting pieces in northEast of clicked Button
        northEast.x = btnAxis.x + 1;
        northEast.y = btnAxis.y + 1;

        if ((northEast.x <= 7) && (northEast.y <= 7)
                && !(button[northEast.x][northEast.y].getBackground().equals(gameBoardColour))) {

            while ((northEast.x <= 7) && (northEast.y <= 7)
                    && (button[northEast.x][northEast.y].getBackground().equals(changeColour))) {
                northEast.y++;
                northEast.x++;
            }

            if ((northEast.x == 8) || (northEast.y == 8)
                    || (button[northEast.x][northEast.y].getBackground().equals(gameBoardColour))) {
                // do nothing
            }

            else
                for (int i = (northEast.y - 1); i >= btnAxis.y; i--) {
                    northEast.x--;
                    button[northEast.x][i].setBackground(currentPlayerColor);
                }
        }

        Coordinates southEast = tempBtnCoordinates;
        // for converting pieces in southEast of clicked Button
        southEast.x = btnAxis.x + 1;
        southEast.y = btnAxis.y - 1;

        if ((southEast.x <= 7) && (southEast.y >= 0)
                && !(button[southEast.x][southEast.y].getBackground().equals(gameBoardColour))) {

            while ((southEast.x <= 7) && (southEast.y >= 0)
                    && (button[southEast.x][southEast.y].getBackground().equals(changeColour))) {
                southEast.y--;
                southEast.x++;
            }

            if ((southEast.x == 8) || (southEast.y == -1)
                    || (button[southEast.x][southEast.y].getBackground().equals(gameBoardColour))) {
                // do nothing
            }

            else
                for (int i = (southEast.y + 1); i <= btnAxis.y; i++) {
                    southEast.x--;
                    button[southEast.x][i].setBackground(currentPlayerColor);
                }
        }

        Coordinates southWest = tempBtnCoordinates;
        // for converting pieces in southWest of clicked Button
        southWest.x = btnAxis.x - 1;
        southWest.y = btnAxis.y - 1;

        if ((southWest.x >= 0) && (southWest.y >= 0)
                && !(button[southWest.x][southWest.y].getBackground().equals(gameBoardColour))) {

            while ((southWest.x >= 0) && (southWest.y >= 0)
                    && (button[southWest.x][southWest.y].getBackground().equals(changeColour))) {
                southWest.y--;
                southWest.x--;
            }

            if ((southWest.x == -1) || (southWest.y == -1)
                    || (button[southWest.x][southWest.y].getBackground().equals(gameBoardColour))) {
                // do nothing
            }

            else
                for (int i = (southWest.y + 1); i <= btnAxis.y; i++) {
                    southWest.x++;
                    button[southWest.x][i].setBackground(currentPlayerColor);
                }
        }

        // Sets all invalid buttons disabled
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                button[i][j].setForeground(Color.gray);
                if ((button[i][j].getBackground().equals(Color.lightGray)))
                    button[i][j].setEnabled(false);
            }
        }
    }

    /**
     * To count the number of moves avaliables left to play
     */
    public int countMovesAvaliable(Coordinates btnAxis, Color currentPlayerColor) {
        int countNoOfFlippable = 0;

        if (isRedPlayerTurn)
            changeColour = Color.green;

        else
            changeColour = Color.red;

        Coordinates left = tempBtnCoordinates;
        // checking for converting pieces in left(West) of clicked Button
        left.x = btnAxis.x - 1;
        left.y = btnAxis.y;

        if ((left.x >= 0) && !(button[left.x][left.y].getBackground().equals(gameBoardColour))) {

            while ((left.x >= 0) && (button[left.x][left.y].getBackground().equals(changeColour)))
                left.x--;

            if ((left.x == -1) || (button[left.x][left.y].getBackground().equals(gameBoardColour))) {
                // do nothing
            } else
                for (int i = (left.x + 1); i <= (btnAxis.x - 1); i++)
                    countNoOfFlippable++;
        }

        Coordinates rightSide = tempBtnCoordinates;
        // checking for converting pieces in rightSide(East) of clicked Button
        rightSide.x = btnAxis.x + 1;
        rightSide.y = btnAxis.y;

        if ((rightSide.x <= 7) && !(button[rightSide.x][rightSide.y].getBackground().equals(gameBoardColour))) {

            while ((rightSide.x <= 7) && (button[rightSide.x][rightSide.y].getBackground().equals(changeColour)))
                rightSide.x++;

            if ((rightSide.x == 8) || (button[rightSide.x][rightSide.y].getBackground().equals(gameBoardColour))) {
                // do nothing
            } else
                for (int i = (rightSide.x - 1); i >= (btnAxis.x + 1); i--)
                    countNoOfFlippable++;
        }

        Coordinates north = tempBtnCoordinates;
        // checking for converting pieces in north of clicked Button
        north.x = btnAxis.x;
        north.y = btnAxis.y + 1;

        if ((north.y <= 7) && !(button[north.x][north.y].getBackground().equals(gameBoardColour))) {

            while ((north.y <= 7) && (button[north.x][north.y].getBackground().equals(changeColour)))
                north.y++;

            if ((north.y == 8) || (button[north.x][north.y].getBackground().equals(gameBoardColour))) {

                // do nothing
            } else
                for (int i = (north.y - 1); i >= (btnAxis.y + 1); i--)
                    countNoOfFlippable++;
        }

        Coordinates south = tempBtnCoordinates;
        // checking for converting pieces in south of clicked Button
        south.x = btnAxis.x;
        south.y = btnAxis.y - 1;

        if ((south.y >= 0) && !(button[south.x][south.y].getBackground().equals(gameBoardColour))) {

            while ((south.y >= 0) && (button[south.x][south.y].getBackground().equals(changeColour)))
                south.y--;

            if ((south.y == -1) || (button[south.x][south.y].getBackground().equals(gameBoardColour))) {

                // do nothing
            } else
                for (int i = (south.y + 1); i <= (btnAxis.y - 1); i++)
                    countNoOfFlippable++;
        }

        Coordinates northWest = tempBtnCoordinates;
        // checking for converting pieces in NW of clicked Button
        northWest.x = btnAxis.x - 1;
        northWest.y = btnAxis.y + 1;

        if ((northWest.x >= 0) && (northWest.y <= 7)
                && !(button[northWest.x][northWest.y].getBackground().equals(gameBoardColour))) {

            while ((northWest.x >= 0) && (northWest.y <= 7)
                    && (button[northWest.x][northWest.y].getBackground().equals(changeColour))) {
                northWest.y++;
                northWest.x--;
            }

            if ((northWest.x == -1) || (northWest.y == 8)
                    || (button[northWest.x][northWest.y].getBackground().equals(gameBoardColour))) {
                // do nothing
            } else
                for (int i = (northWest.y - 1); i >= (btnAxis.y + 1); i--) {
                    tempBtnCoordinates.x++;
                    countNoOfFlippable++;
                }
        }

        Coordinates northEast = tempBtnCoordinates;
        // checking for converting pieces in NE of clicked Button
        northEast.x = btnAxis.x + 1;
        northEast.y = btnAxis.y + 1;

        if ((northEast.x <= 7) && (northEast.y <= 7)
                && !(button[northEast.x][northEast.y].getBackground().equals(gameBoardColour))) {

            while ((northEast.x <= 7) && (northEast.y <= 7)
                    && (button[northEast.x][northEast.y].getBackground().equals(changeColour))) {
                northEast.y++;
                northEast.x++;
            }

            if ((northEast.x == 8) || (northEast.y == 8)
                    || (button[northEast.x][northEast.y].getBackground().equals(gameBoardColour))) {
                // do nothing
            } else
                for (int i = (northEast.y - 1); i >= (btnAxis.y + 1); i--) {
                    northEast.x--;
                    countNoOfFlippable++;
                }
        }

        Coordinates southEast = tempBtnCoordinates;
        // checking for converting pieces in SE of clicked Button
        southEast.x = btnAxis.x + 1;
        southEast.y = btnAxis.y - 1;

        if ((southEast.x <= 7) && (southEast.y >= 0)
                && !(button[southEast.x][southEast.y].getBackground().equals(gameBoardColour))) {

            while ((southEast.x <= 7) && (southEast.y >= 0)
                    && (button[southEast.x][southEast.y].getBackground().equals(changeColour))) {
                southEast.y--;
                southEast.x++;
            }

            if ((southEast.x == 8) || (southEast.y == -1)
                    || (button[southEast.x][southEast.y].getBackground().equals(gameBoardColour))) {
                // do nothing
            } else
                for (int i = (southEast.y + 1); i <= (btnAxis.y - 1); i++) {
                    southEast.x--;
                    countNoOfFlippable++;
                }
        }

        Coordinates southWest = tempBtnCoordinates;
        // checking for converting nodes in SW of clicked Button
        southWest.x = btnAxis.x - 1;
        southWest.y = btnAxis.y - 1;

        if ((southWest.x >= 0) && (southWest.y >= 0)
                && !(button[southWest.x][southWest.y].getBackground().equals(gameBoardColour))) {

            while ((southWest.x >= 0) && (southWest.y >= 0)
                    && (button[southWest.x][southWest.y].getBackground().equals(changeColour))) {
                southWest.y--;
                southWest.x--;
            }

            if ((southWest.x == -1) || (southWest.y == -1)
                    || (button[southWest.x][southWest.y].getBackground().equals(gameBoardColour))) {
                // do nothing
            } else
                for (int i = (southWest.y + 1); i <= (btnAxis.y - 1); i++) {
                    southWest.x++;
                    countNoOfFlippable++;
                }
        }
        return countNoOfFlippable;
    }

    /**
     * To obtain the current scoreBoard of the game by displaying total of discs
     * obtained by each player
     */
    public void getGameStatus(Color playerColour) {
        Color nextPlayerColour, currentPlayerColour;
        int countMovesAvaliableForNextPlayer = 0, countMovesAvaliableForCurrentPlayer = 0;
        int redScore = 0, greenScore = 0;
        // score Calculation
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if (button[i][j].getBackground().equals(Color.red))
                    redScore++;

                else if (button[i][j].getBackground().equals(Color.green))
                    greenScore++;
            }
        }

        scores[0].setText("" + redScore);
        scores[1].setText("" + greenScore);
        // obtaining status of game
        isRedPlayerTurn = !isRedPlayerTurn;

        if (isRedPlayerTurn)
            nextPlayerColour = Color.red;

        else
            nextPlayerColour = Color.green;

        showNextMoves(nextPlayerColour);
        // counting possible moves for next player
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (button[i][j].getForeground().equals(Color.black))
                    countMovesAvaliableForNextPlayer++;
            }
        }

        if (countMovesAvaliableForNextPlayer == 0) {
            // next player has no valid move
            isRedPlayerTurn = !isRedPlayerTurn;
            if (isRedPlayerTurn)
                currentPlayerColour = Color.red;

            else
                currentPlayerColour = Color.green;

            showNextMoves(currentPlayerColour);
            // counting possible moves for current player
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (button[i][j].getForeground().equals(Color.black))
                        countMovesAvaliableForCurrentPlayer++;
                }
            }

            if (countMovesAvaliableForCurrentPlayer == 0) {
                // current player also has no valid move
                display.setText(" ");

                if (redScore == greenScore)
                    JOptionPane.showMessageDialog(this, "THIS MATCH IS A DRAW!");

                else if (redScore > greenScore)
                    JOptionPane.showMessageDialog(this, "CONGRATULATIONS \n" + username1 + " WINS!");

                else
                    JOptionPane.showMessageDialog(this, "CONGRATULATIONS  \n" + username2 + " WINS!");
            }

            else {

                if (isRedPlayerTurn) {
                    display.setText(username2 + ":NO VALID MOVE." + username1 + "'s turn ");
                    playerColour = Color.red;
                }

                else {
                    display.setText(username1 + ":NO VALID MOVE." + username2 + "'s turn");
                    playerColour = Color.green;
                }
            }
        } else {

            if (isRedPlayerTurn) {
                display.setText(username1 + "'s turn");
                playerColour = Color.red;
            }

            else {
                display.setText(username2 + "'s turn");
                playerColour = Color.green;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        JButton clickedButton = (JButton) ae.getSource();
        Color playerColour;
        Coordinates btnAxis = temporarySourcebtnAxis;

        if (clickedButton.getBackground().equals(gameBoardColour)) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (clickedButton == button[i][j]) {
                        btnAxis.x = i;
                        btnAxis.y = j;
                    }
                }
            }

            if (isRedPlayerTurn)
                playerColour = Color.red;

            else
                playerColour = Color.green;
            // clicked Button's background need not be changed here as it is changed in
            // changeColour
            changeColour(btnAxis, playerColour);
            getGameStatus(playerColour);

        } else if (clickedButton == newGame) {
            int n = JOptionPane.showConfirmDialog(this, "Would you like to start a new game?", "",
                    JOptionPane.YES_NO_OPTION);
            if (n == 0)
                initialise();
        } else
            JOptionPane.showMessageDialog(this, "Invalid move!", "Try Again!", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {

        reversiGameApplication myNewApp = new reversiGameApplication();
        myNewApp.makeFrame();
    }

}
