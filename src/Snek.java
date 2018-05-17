import java.awt.event.KeyEvent;


public class Snek {
	public static SnekBody snekBody;
  public static void main(String[] args) {
    //Main class for running the basic Snake game
	snekBody = SnekBody.getInstance();
	 
    SnekBoard.initThread();
    while(true) {
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
