//Import necessary java packages
import javax.swing.UIManager;

/***********************
  * PuzzleDriver.java
  * Authors: Billy Zhu and Vincent Yu
  * Demonstrates the functionality of the Puzzle class.
  ************************/
public class PuzzleDriver
{
  
  //Main Method
  public static void main(String[] args)
  {
    //Shoutout to Mac users you guys have nice keyboards
    try {
      UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
    } catch (Exception e) {
      e.printStackTrace();
    }
    //Contructs a Puzzle object(the Puzzle game), sets the dimensions, and sets the game visible to the user
    Puzzle puz = new Puzzle();
    puz.setBounds(20, 20, 300, 325);
    puz.setVisible(true);
    
  } //closes main method
  
} //closes PuzzleDriver class