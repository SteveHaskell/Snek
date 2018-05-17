import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class SnekBoard extends JFrame implements KeyListener {

  private static final long serialVersionUID = 1L;

  private static SnekBody snekBody;

  private int unitHeight;
  private int unitWidth;
  private int boardHeight = 1000;
  private int boardWidth = 1000;
  public static int gameCycleMilli = 1000;

  public SnekBoard() {
    addKeyListener(this);
    snekBody = SnekBody.getInstance();
    snekBody.updatePowerCube();
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

  @Override
  public void keyPressed(KeyEvent e) { // TODO Auto-generated method stub
    String s = "empty";
    if (e.getKeyCode() == KeyEvent.VK_UP) {
      snekBody.updateDirection(0);
    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
      snekBody.updateDirection(1);
    } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      snekBody.updateDirection(2);
    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      snekBody.updateDirection(3);
    }
  }

  public void paint(Graphics g) {
    Snek.wait(10);

    if (!snekBody.gameOver) {

      for (int i = 0; i < snekBody.maxWidth; i++) {
        for (int j = 0; j < snekBody.maxHeight; j++) {
          int x = i * unitWidth;
          int y = j * unitHeight;
          if (snekBody.bodySpaces[i][j] == 1) {
            g.setColor(Color.blue);
            g.fillRect(x, y, unitWidth, unitHeight);
          } else if (i == snekBody.cubeX && j == snekBody.cubeY) {
            //do nothing
          } else {
            g.setColor(getBackground());
            g.fillRect(x, y, unitWidth, unitHeight);
          }
        }
      }
      //place a power cube if we need it
      if (snekBody.needNewCube) {
        snekBody.needNewCube = false;
        g.setColor(getBackground());
        g.fillOval(snekBody.cubeX*unitWidth, snekBody.cubeY*unitHeight, unitWidth, unitHeight);
        snekBody.updatePowerCube();
      }
      g.setColor(Color.green);
      g.fillOval(snekBody.cubeX*unitWidth, snekBody.cubeY*unitHeight, unitWidth, unitHeight);

    } else {
      g.setColor(Color.red);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 32));
      g.drawString("Game Over!", boardWidth / 2, boardHeight / 2);
    }
  }

  @Override
  public void keyTyped(KeyEvent e) { // TODO Auto-generated method stub
  }

  @Override
  public void keyReleased(KeyEvent e) { // TODO Auto-generated method stub
  }
}
