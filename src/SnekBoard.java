import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

/*
 * Class to handle painting the Snek Board and listening to key presses
 */
public class SnekBoard extends JFrame implements KeyListener {

  private static final long serialVersionUID = 1L;

  private static SnekBody snekBody;

  private int unitHeight;
  private int unitWidth;
  private int boardHeight = 1000;
  private int boardWidth = 1000;
  

  public SnekBoard() {
    addKeyListener(this); //allows us to get keypresses from the keyboard
    snekBody = SnekBody.getInstance();
    unitHeight = boardHeight / snekBody.maxHeight;
    unitWidth = boardWidth / snekBody.maxWidth;

    setSize(new Dimension(boardHeight, boardWidth));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }
  /*
   * Thread that controls painting the Snake on the board
   */
  public static void initThread() {
    (new Thread(
            new Runnable() {

              @Override
              public void run() {
                SnekBoard snekBoard = new SnekBoard();
                while (true) snekBoard.repaint();
              }
            }))
        .start();
  }

  public void paint(Graphics g) {
    Snek.wait(10);

    //paint the snake body
    for (int i = 0; i < snekBody.maxWidth; i++) {
      for (int j = 0; j < snekBody.maxHeight; j++) {
        int x = i * unitWidth;
        int y = j * unitHeight;
        if (snekBody.bodySpaces[i][j] == 1) {
          g.setColor(Color.blue);
          g.fillRect(x, y, unitWidth, unitHeight);
        } else {
          g.setColor(getBackground());
          g.fillRect(x, y, unitWidth, unitHeight);
        }
      }
    }
    //drawPowerCube
    if(!snekBody.youWin) {
    g.setColor(Color.green);
    g.fillOval(snekBody.cubeX * unitWidth, snekBody.cubeY * unitHeight, unitWidth, unitHeight);
    }
    if (snekBody.gameOver) {
      g.setColor(Color.red);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 32));
      g.drawString("Game Over!", boardWidth / 2, boardHeight / 2);
    }
    if (snekBody.youWin) {
      g.setColor(Color.green);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 64));
      g.drawString("YOU WIN!!", boardWidth / 2, boardHeight / 2);
    }
  }

  /*
   * Be sure to pass info right to snekBody so that it can handle the main game loop
   */
  @Override
  public void keyPressed(KeyEvent e) { // TODO Auto-generated method stub
    snekBody.keyPressed(e);
  }

  @Override
  public void keyTyped(KeyEvent e) {
    snekBody.keyTyped(e);
  }

  @Override
  public void keyReleased(KeyEvent e) { // TODO Auto-generated method stub
  }
}
