import java.util.Random;

public class SnekBody {
	
	private static SnekBody sInstance;
	
	public int maxHeight = 10; //number of units tall the snake can be-
	public int maxWidth  = 10; // number of units wide the snake can be
	public int[][] bodySpaces; // a 1 means that space is occupied with snake body
	public int[] xTrack;
	public int[] yTrack;
	public int maxLength = 9999;
	public int length;

	public int segmentCount;
	public int xLoc = 0;
	public int yLoc = 0;
	public int direction = 2; //0,1,2,3 = left,right,up,down
	public int cubeX = 0;
	public int cubeY = 0;
	
	public boolean gameOver;
	public boolean needNewCube;
	public SnekBody() {
		xLoc = maxWidth/2;
		yLoc = maxHeight/2;
		gameOver = false;
		bodySpaces = new int[maxWidth][maxHeight];
			
		for (int i=0;i<maxWidth; i++) {
			for (int j=0;j<maxHeight; j++) {
				bodySpaces[i][j] = 0;
			}	
		}
		length = 5;
		//below used to track the length of the snake
		xTrack = new int[maxLength]; //
		yTrack = new int[maxLength];
		segmentCount = 0;
		
		needNewCube = true;
	}
	public void updateDirection(int newDirection) {
		if(newDirection>=0 && newDirection<=3) {
			direction = newDirection;
		}
		else {
			direction = 0;
		}
	}
	public void updatePowerCube() {
		do {
		Random rand = new Random();
		cubeX = rand.nextInt(maxWidth);
		cubeY = rand.nextInt(maxHeight);
		}while(bodySpaces[cubeX][cubeY]==1);
			
	}
	public void updateBody() {
		Snek.wait(SnekBoard.gameCycleMilli);
		switch(direction) {
		case 0: yLoc = yLoc - 1; //up
				break;	
		case 1:	yLoc = yLoc + 1; //down
				break;	
		case 2:	xLoc = xLoc - 1; //left
				break;	
		case 3: xLoc = xLoc + 1; //right
				break;	
		}
		boolean borderCheck = (xLoc<0||xLoc>=maxWidth||yLoc<0||yLoc>=maxHeight);
		
		if(borderCheck ||  gameOver) {
			gameOver = true;
		}
		else if(bodySpaces[xLoc][yLoc] == 1) {
			gameOver = true;
		}
		else {
			bodySpaces[xLoc][yLoc] = 1;
			xTrack[segmentCount] = xLoc;
			yTrack[segmentCount] = yLoc;
			segmentCount++;
			if(segmentCount>=length){
				int delta = segmentCount - length;
				int x = xTrack[delta];
				int y = yTrack[delta];
				bodySpaces[x][y]=0;
			}
			
			if(xLoc==cubeX && yLoc==cubeY) {
				//we got a cube!
				length = length+5;
				needNewCube = true;
			}
			
			System.out.println("xLoc: "+xLoc+", yLoc: "+yLoc+" SegmentCount: "+segmentCount + " cubeX: "+cubeX+" cubeY: "+cubeY);
		}
	}
	public static SnekBody getInstance() {
	    if (sInstance == null) {
	      sInstance = new SnekBody();
	    }

	    return sInstance;
	  }
}
