import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;

public class SnekBoard extends JFrame {
  /** */
  private static final long serialVersionUID = 1L;
  private static SnekBody snekBody;
  private int unitHeight;
  private int unitWidth;
  private int boardHeight = 500;
  private int boardWidth = 500;
  
  public SnekBoard() {
	snekBody = SnekBody.getInstance();
	unitHeight = boardHeight/snekBody.maxHeight;
	unitWidth = boardWidth/snekBody.maxWidth;

	
    setSize(new Dimension(boardHeight, boardWidth));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }
  //Should only handle drawing, no decisions made in paint
  public void paint(Graphics g) {
	wait(500);
	System.out.println("painting");

    g.setColor(Color.blue);
    for(int i=0;i<snekBody.maxWidth;i++) {
    	for(int j=0;j<snekBody.maxHeight;j++) {
    		if( snekBody.bodySpaces[i][j] == 1) {
    			int x = i*unitWidth;
    			int y = j*unitHeight;
    			g.fillRect(x, y, unitWidth, unitHeight);
    		}
    	}
    }
    
  }
  public static void wait(int waitTime) {
	    try {
	      Thread.sleep(waitTime);
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    }
	  }
}
