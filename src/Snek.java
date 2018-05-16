import javax.swing.SwingUtilities;

public class Snek {
	public static SnekBody snekBody;
  public static void main(String[] args) {
    //Main class for running the basic Snake game
	snekBody = SnekBody.getInstance();
	 
   /*
    * Thread that controls painting the Snake on the board
    */
    (new Thread(
        new Runnable() {

          @Override
          public void run() {
        	SnekBoard snekBoard = new SnekBoard();
            while(true) snekBoard.repaint();
          }
        })).start();
    /*
     * get direction input from keyboard
     */
    for (int i = 0; i < 10; i++) {
        wait(1000);
        System.out.println("udpating body");
        snekBody.updateBody();
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
