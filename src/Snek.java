

public class Snek {
  public static SnekBody snekBody;

  public static void main(String[] args) {
    //Main class for running the basic Snake game

    snekBody = SnekBody.getInstance();

    SnekBoard.initThread(); //constantly painting
    while(true) {
    snekBody.updateBody(); //main game loop
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
