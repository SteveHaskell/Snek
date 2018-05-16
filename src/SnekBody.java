
public class SnekBody {
	
	private static SnekBody sInstance;
	
	public int maxHeight = 20; //number of units tall the snake can be-
	public int maxWidth  = 20; // number of units wide the snake can be
	public int[][] bodySpaces; // a 1 means that space is occupied with snake body
	public int xLoc = 5;
	public int yLoc = 5;
	public int direction = 2; //0,1,2,3 = left,right,up,down
	public SnekBody() {
		bodySpaces = new int[maxWidth][maxHeight];
			
		for (int i=0;i<maxWidth; i++) {
			for (int j=0;j<maxHeight; j++) {
				bodySpaces[i][j] = 0;
			}	
		}
		
		
	}
	public void updateBody() {
		switch(direction) {
		case 0: xLoc = xLoc - 1;
				break;	
		case 1:	xLoc = xLoc + 1;
				break;	
		case 2:	yLoc = yLoc + 1;
				break;	
		case 3:yLoc = yLoc - 1;
				break;	
		}
		
		bodySpaces[xLoc][yLoc] = 1;
	}
	public static SnekBody getInstance() {
	    if (sInstance == null) {
	      sInstance = new SnekBody();
	    }

	    return sInstance;
	  }
}
