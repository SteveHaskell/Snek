import java.awt.event.KeyEvent;
import java.util.Random;

public class SnekBody {

  private static SnekBody sInstance;

  public int[][] bodySpaces; // a 1 means that space is occupied with snake body
  public int[] xTrack;
  public int[] yTrack;
  public int maxHeight = 10; //must be even number!
  public int maxWidth = 10; //must be even number!
  public int maxSegmentCount = 9999;
  public int length = 0;
  public int maxLength = 5;
  public int cubeExtra = 2;

  public int segmentCount = 1;
  public int xLoc = 0;
  public int yLoc = 0;
  public int direction = 3; //3: to the right
  public int cubeX = 0;
  public int cubeY = 0;
  public int maxCycleMilli = 1000; //slowest speed
  public int minCycleMilli = 500; //fastest speed
  public int gameCycleMilli = maxCycleMilli;
  public boolean showStatus = true;
  public boolean gameOver = false;
  public boolean needNewCube = true;
  public boolean pauseGame = false;
  public boolean youWin = false;

  public SnekBody() {
    bodySpaces = new int[maxWidth][maxHeight];

    for (int i = 0; i < maxWidth; i++) {
      for (int j = 0; j < maxHeight; j++) {
        bodySpaces[i][j] = 0;
      }
    }
    //add initial starting unit
    bodySpaces[xLoc][yLoc] = 1;
    length++;
    segmentCount++;
    //below used to track the length of the snake
    xTrack = new int[maxSegmentCount]; //
    yTrack = new int[maxSegmentCount];
    updatePowerCube();
  }

  public void updateBody() {
    Snek.wait(gameCycleMilli);
    if (!pauseGame && !youWin && !gameOver) {
      mainSnekLoop();
      status();
    }
  }

  public void mainSnekLoop() {

    //determine which direction we will move, advance one unit in that direction
    switch (direction) {
      case 0:
        yLoc = yLoc - 1; //up
        break;
      case 1:
        yLoc = yLoc + 1; //down
        break;
      case 2:
        xLoc = xLoc - 1; //left
        break;
      case 3:
        xLoc = xLoc + 1; //right
        break;
    }

    //add new segment to our list
    xTrack[segmentCount] = xLoc;
    yTrack[segmentCount] = yLoc;
    segmentCount++;

    //remove the tail as snake gets longer than it's current length
    if (length >= maxLength) {
      int delta = segmentCount - length-1;
      int x = xTrack[delta];
      int y = yTrack[delta];
      bodySpaces[x][y] = 0; //delete a segment off the tail
    }
    //game over if you hit the walls
    if (xLoc < 0 || xLoc >= maxWidth || yLoc < 0 || yLoc >= maxHeight) {
      gameOver = true;
    }
    //game over if you hit yourself
    else if (bodySpaces[xLoc][yLoc]==1) {
      gameOver = true;
    } else {
      //update the head location and length if you didn't die
      bodySpaces[xLoc][yLoc] = 1;
      if (length < maxLength) {
        length++;
      }
    }

    //check to see if we ate a power cube
    if (xLoc == cubeX && yLoc == cubeY) {
      //we got a cube!
      maxLength = maxLength + cubeExtra;
      updatePowerCube();
      increaseSpeed();
      
    }

    checkForWin();
  }

  public boolean checkForWin() {
    boolean res = true;
    for (int i = 0; i < maxWidth; i++) {
      for (int j = 0; j < maxHeight; j++) {
        if (bodySpaces[i][j] == 0) {
          res = false;
        }
      }
    }
    youWin = res;
    return res;
  }
  public void increaseSpeed() {
	  if(gameCycleMilli>minCycleMilli) {
	  gameCycleMilli = gameCycleMilli - 100;
	  }
  }
  public void updateDirection(int newDirection) {
    if (newDirection >= 0 && newDirection <= 3) {
      direction = newDirection;
    } else {
      direction = 0;
    }
  }

  public void updatePowerCube() {
    do {
      Random rand = new Random();
      cubeX = rand.nextInt(maxWidth);
      cubeY = rand.nextInt(maxHeight);
    } while (bodySpaces[cubeX][cubeY] == 1 && !checkForWin());
  }

  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_UP) {
      updateDirection(0);
    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
      updateDirection(1);
    } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      updateDirection(2);
    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      updateDirection(3);
    }
  }

  public void keyTyped(KeyEvent e) {
    if (e.getKeyChar() == KeyEvent.VK_SPACE) {
      pauseGame = !pauseGame;
    }
  }
  //print out relevant stuff to console for debug and observations
  public void status() {
    if (showStatus) {
      System.out.print("xLoc:" + xLoc + ", yLoc:" + yLoc);
      System.out.print(" SegmentCount:" + segmentCount);
      System.out.print(" cubeX:" + cubeX + " cubeY:" + cubeY);
      System.out.println(" Direction:" + direction);
      System.out.println("length:" + length + " maxLength:" + 
    		  maxLength +" segmentCount:"+segmentCount);
      System.out.println("gameCycleMilli:"+gameCycleMilli);
      System.out.println("pauseGame: " + pauseGame);
      System.out.println("youWin:"+youWin);

      for (int i = 0; i < maxWidth; i++) {
        for (int j = 0; j < maxHeight; j++) {
          System.out.print(bodySpaces[j][i] + " ");
        }
        System.out.println("");
      }
    }
  }

  public static SnekBody getInstance() {
    if (sInstance == null) {
      sInstance = new SnekBody();
    }

    return sInstance;
  }
}
