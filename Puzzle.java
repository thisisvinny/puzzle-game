//Import necessary java packages
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

/***********************
  * Puzzle.java
  * Authors: Billy Zhu and Vincent Yu
  * Creates the GUI for a picture puzzle game
  ************************/
public class Puzzle extends JFrame implements ActionListener
{
  //Declares and instantiates necessary JPanel
  private JPanel centerPanel = new JPanel();
  private JPanel northPanel = new JPanel();
  private JPanel southPanel = new JPanel();
  
  //Declares and instantiates JButtons and JButton array
  private JButton finished = new JButton("I finished");
  private JButton newGame = new JButton("New Game"); 
  private JButton[] btArray = new JButton[8];
  
  //Declares JLabels
  private JLabel moves = new JLabel("Moves : ");
  private JLabel label;
  
  //Declares instance variables
  private Container mainPanel = this.getContentPane();
  private int[] randomIndex = {0, 1, 2, 3, 4, 5, 6, 7};
  private int[][] pos;
  private int count;
  private String str;
   
  //Constructor method
  public Puzzle()
  {
    super("Puzzle Game");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    userInterface();
  }
  
  //----------------------------------------
  //  userInterface()
  //  Creates the UI for the puzzle game.
  //----------------------------------------
  public void userInterface()
  {
    //2D array -- the 3x3 game board
    pos = new int[][] {
      {0, 1, 2},
      {3, 4, 5},
      {6, 7, 8},
    };
    
    //Initialize button array
    for (int i = 0; i < 8; i++)
    {
      btArray[i] = new JButton("B" + i);
    }
    
    //Sets a picture to each button
    btArray[0].setIcon(new ImageIcon(Puzzle.class.getResource("1.jpg")));
    btArray[1].setIcon(new ImageIcon(Puzzle.class.getResource("2.jpg")));
    btArray[2].setIcon(new ImageIcon(Puzzle.class.getResource("3.jpg")));
    btArray[3].setIcon(new ImageIcon(Puzzle.class.getResource("4.jpg")));
    btArray[4].setIcon(new ImageIcon(Puzzle.class.getResource("5.jpg")));
    btArray[5].setIcon(new ImageIcon(Puzzle.class.getResource("6.jpg")));
    btArray[6].setIcon(new ImageIcon(Puzzle.class.getResource("7.jpg")));
    btArray[7].setIcon(new ImageIcon(Puzzle.class.getResource("8.jpg")));
    
    //North Panel - buttons "I finished" and "New Game"
    //Adds North Panel to main game board
    newGame.setFocusable(false);
    finished.setFocusable(true);
    finished.addActionListener(this);
    newGame.addActionListener(this);
    northPanel.setBackground(Color.WHITE);
    northPanel.add(finished);
    northPanel.add(newGame);
    mainPanel.add(northPanel, BorderLayout.NORTH);
    
    //South Panel - move counter
    //Adds South Panel to main game board
    southPanel.add(moves);
    southPanel.setBackground(Color.WHITE);
    mainPanel.add(southPanel, BorderLayout.SOUTH);
    
    //Game Panel
    centerPanel.setLayout(new GridLayout(3, 3, 0, 0));
    centerPanel.setBackground(Color.LIGHT_GRAY);
    
    //Add actionListeners to each button
    for (int i = 0; i < 8; i++)
    {
      btArray[i].addActionListener(this);
    }
    
    //Starts a new game by removing the buttons and placing them back onto the board at random positions
    //Resets the move counter to 0
    for (int j = 7; j >= 0; j--)
    {
      centerPanel.remove(btArray[j]);
    }
    for (int j: shuffleArray(randomIndex))
    {
      centerPanel.add(btArray[j]);
    }
    centerPanel.revalidate();
    count = 0;
    mainPanel.invalidate();
    mainPanel.validate();
    
    //Adds an empty piece to the picture in the form of an empty JLabel
    label = new JLabel("Empty Piece");
    label.setBackground(Color.BLACK);
    centerPanel.add(label);
    mainPanel.add(centerPanel);
    setResizable(false);
    
    //Instructions Panel -- explains how the game is played and won
    ImageIcon wonGame = new ImageIcon(Puzzle.class.getResource("/WonGame.jpg"));
    JOptionPane.showMessageDialog(
                                  null,
                                  new JLabel("Using the mouse, organize the board to the following image."
                                               + " When you're finished, click the \"I finished\" button",
                                             wonGame, JLabel.LEFT),
                                  "Instructions", JOptionPane.INFORMATION_MESSAGE);
  } //closed method userInterface()
  
  //---------------------------------------------
  //  shuffeArray(int[] ar)
  //  Randomizes the button array so when "New Game" is clicked the game board is randomized
  //
  //  Precondition: paramater ar is filled with integer values
  //  Postcondition: integer values of the array have been shuffled around
  //---------------------------------------------
  public static int[] shuffleArray(int[] ar)
  {
    Random rnd = new Random();
    for (int i = ar.length - 1; i > 0; i--)
    {
      int index = rnd.nextInt(i + 1);
      // Simple swap
      int a = ar[index];
      ar[index] = ar[i];
      ar[i] = a;
    }
    return ar;
  } //closes method shuffleArray(int[] ar)
  
  //----------------------------------------
  //  isFinished()
  //  Checks if the Puzzle Game has been finished.
  //----------------------------------------
  public boolean isFinished()
  {
    //The logic of the method:
    //   First If statement checks if the y-coordinates of buttons 0-2 are equal (meaning they are all on the same row of the game board)
    //   AND the y-coordinates of buttons 3-5 are equal AND the y-coordinates of buttons 6-7 are equal
    if ((btArray[0].getY() == btArray[1].getY() && btArray[1].getY() == btArray[2].getY()) &&
        (btArray[3].getY() == btArray[4].getY() && btArray[4].getY() == btArray[5].getY())
          && (btArray[6].getY() == btArray[7].getY()))
    {
      //Second If statement checks if the x-coordinates of buttons 0,3,6 are equal (meaning they are all on the same column of the game board)
      //AND the x-coordinates of buttons 1,4,7 are equal AND the x-coordinates of buttons 3 and 5 are equal
      if (btArray[0].getX() == btArray[3].getX() && btArray[3].getX() == btArray[6].getX()
            && btArray[1].getX() == btArray[4].getX() && btArray[4].getX() == btArray[7].getX()
            && btArray[2].getX() == btArray[5].getX())
      {
        //If the conditions for the two If statements above have been met, then the game has been finished -- return true
        return true;
      }
    }
    //If either of the two If statements above were not true, then the game has not been finished -- return false
    return false;
  } //closed method isFinished()
  
  //---------------------------------------------
  //  actionPerformed(ActionEvent ae)
  //  Action Events for action listeners.
  //---------------------------------------------
  public void actionPerformed(ActionEvent ae)
  {
    JButton button = (JButton) ae.getSource();
    Dimension size = button.getSize();
    
    //If "I finished" button is clicked...
    if(ae.getSource() == finished)
    {
      //If the user did win, print a congratulations message with number of moves needed to finish
      if (isFinished() && count > 0)
        JOptionPane.showMessageDialog(null, "Well done\n It took you " + count+ " moves to finish the puzzle"
                                        + "\n To play a new game, hit the \"New Game\" button");
      //If the user did not win, print a message that tells them to keep trying
      else
        JOptionPane.showMessageDialog(null, "Keep on trying");
    }
    
    //If "New Game" button is clicked then start a new game
    if (ae.getSource() == newGame)
    {
      this.setVisible(false);
      Puzzle puz = new Puzzle();
      puz.setBounds(20, 20, 300, 325);
      puz.setVisible(true);
    }
    
    //Gets x and y coordinates of the empty label, the button clicked, the button's position relative to the game board, and the button's index in the 2D Array
    int labelX = label.getX();
    int labelY = label.getY();
    int buttonX = button.getX();
    int buttonY = button.getY();
    int buttonPosX = buttonX / size.width;
    int buttonPosY = buttonY / size.height;
    int buttonIndex = pos[buttonPosY][buttonPosX];
    
    //If x-coordinate of the label is equal to the x-coordinate of the button clicked...
    //AND if y-coordinate of the label minus the y-coordinate of the button clicked is equal to 3...
    //This means that the label is below the button
    //Remove the button, add an empty label at the button's position, and re-add the button on the game board at the old empty label's position
    //Also, increase the value of count by 1 (count represents the number of moves done in the game)
    if (labelX == buttonX && (labelY - buttonY) == size.height) {
      int labelIndex = buttonIndex + 3;
      centerPanel.remove(buttonIndex);
      centerPanel.add(label, buttonIndex);
      centerPanel.add(button, labelIndex);
      centerPanel.validate();
      count++;
    }
    
    //If x-coordinate of the label is equal to the x-coordinate of the button clicked...
    //AND if y-coordinate of the label minus the y-coordinate of the button clicked is equal to -3...
    //This means that the label is above the button
    //Remove the button, add an empty label at the button's position, and re-add the button on the game board at the old empty label's position
    //Also, increase the value of count by 1 (count represents the number of moves done in the game)
    if (labelX == buttonX && (labelY - buttonY) == -size.height) {
      int labelIndex = buttonIndex - 3;
      centerPanel.remove(labelIndex);
      centerPanel.add(button, labelIndex);
      centerPanel.add(label, buttonIndex);
      centerPanel.validate();
      count++;
    }
    
    //If y-coordinate of the label is equal to the y-coordinate of the button clicked...
    //AND if x-coordinate of the label minus the x-coordinate of the button clicked is equal to 1...
    //This means that the label is to the right of the button
    //Remove the button, add an empty label at the button's position, and re-add the button on the game board at the old empty label's position
    //Also, increase the value of count by 1 (count represents the number of moves done in the game)
    if (labelY == buttonY && (labelX - buttonX) == size.width) {
      int labelIndex = buttonIndex + 1;
      centerPanel.remove(buttonIndex);
      centerPanel.add(label, buttonIndex);
      centerPanel.add(button, labelIndex);
      centerPanel.validate();
      count++;
    }
    
    //If y-coordinate of the label is equal to the y-coordinate of the button clicked...
    //AND if x-coordinate of the label minus the x-coordinate of the button clicked is equal to -1...
    //This means that the label is to the left of the button
    //Remove the button, add an empty label at the button's position, and re-add the button on the game board at the old empty label's position
    //Also, increase the value of count by 1 (count represents the number of moves done in the game)
    if (labelY == buttonY && (labelX - buttonX) == -size.width) {
      int labelIndex = buttonIndex - 1;
      centerPanel.remove(buttonIndex);
      centerPanel.add(label, labelIndex);
      centerPanel.add(button, labelIndex);
      centerPanel.validate();
      count++;
    }
    
    //Shows the total number of moves done in the game
    str = String.valueOf(count);         
    moves.setText("Moves : " + str);
    
  } //closes method actionPerformed(ActionEvent ae)
  
  //---------------------------------------------------
  //  getCount()
  //  Returns the value of count (the # of moves done).
  //---------------------------------------------------
  public int getCount() {
    return count;
  }
  
  //---------------------------------------------------
  //  setCount()
  //  Changes the value of count.
  //
  //  Precondition: parameter newCount is an integer value
  //  Postcondition: count is equal to newCount
  //---------------------------------------------------
  public void setCount(int newCount) {
    count = newCount;
  }
  
  //---------------------------------------------------
  //  get2DArray()
  //  Returns the 2D array that represents the game board.
  //---------------------------------------------------
  public int[][] get2DArray() {
    return pos;
  }
  
  //---------------------------------------------------
  //  set2DArray()
  //  Changes the values in the 2D array.
  //
  //  Precondition: parameters must be int values that are different and between 0-8
  //  Postcondition: 2D array is filled with each of the values from 0-8
  //---------------------------------------------------
  public void set2DArray(int pos0, int pos1, int pos2, int pos3, int pos4, int pos5, int pos6, int pos7, int pos8) {
    pos[0][0] = pos0;
    pos[0][1] = pos1;
    pos[0][2] = pos2;
    pos[1][0] = pos3;
    pos[1][1] = pos4;
    pos[1][2] = pos5;
    pos[2][0] = pos6;
    pos[2][1] = pos7;
    pos[2][2] = pos8;
  }
  
  //--------------------------------------------
  //  toString()
  //  Returns the 2D array and value of count of the puzzle game.
  //--------------------------------------------
  public String toString() {
    String summary = "";
    for(int row=0; row<pos.length; row++) {
      for(int column=0; column<pos[row].length; column++) {
        summary += pos[row][column] + " ";
      }
      summary += "\n";
    }
    summary += "Count: " + getCount();
    return summary;
  } //closes method toString()
  
} //closes Puzzle class