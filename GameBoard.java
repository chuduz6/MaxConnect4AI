import java.io.*;

/*
 * @author Chudamani Aryal
 */

public class GameBoard 
{
    // class fields
    public int[][] playBoard;
    public int pieceCount;
    public int currentTurn;

    public GameBoard( String inputFile ) 
    {
	this.playBoard = new int[6][7];
	this.pieceCount = 0;
	int counter = 0;
	BufferedReader input = null;
	String gameData = null;

	// open the input file
	try 
	{
	    input = new BufferedReader( new FileReader( inputFile ) );
	} 
        catch( IOException e ) 
	{
	    System.out.println("\nProblem opening the input file!\nTry again." +
			       "\n");
	    e.printStackTrace();
	}

	//read the game data from the input file
	for(int i = 0; i < 6; i++) 
	{
	    try 
	    {
		gameData = input.readLine();
		
	
		for( int j = 0; j < 7; j++ ) 
		{
		    this.playBoard[ i ][ j ] = gameData.charAt( counter++ ) - 48;
		    
		    // sanity check
		    if( !( ( this.playBoard[ i ][ j ] == 0 ) ||
			   ( this.playBoard[ i ][ j ] == 1 ) ||
			   ( this.playBoard[ i ][ j ] == 2 ) ) ) 
                    {
			System.out.println("\nProblems!\n--The piece read " +
					   "from the input file was not a 1, a 2 or a 0" );
			this.exit_function( 0 );
		    }
		    
		    if( this.playBoard[ i ][ j ] > 0 )
		    {
			this.pieceCount++;
		    }
		}
	    } 
	    catch( Exception e ) 
	    {
		System.out.println("\nProblem reading the input file!\n" +
				   "Try again.\n");
		e.printStackTrace();
		this.exit_function( 0 );
	    }

	    //reset the counter
	    counter = 0;
	    
	} // end for loop

	// read one more line to get the next players turn
	try 
        {
	    gameData = input.readLine();
	} 
	catch( Exception e ) 
	{
	    System.out.println("\nProblem reading the next turn!\n" +
			       "--Try again.\n");
	    e.printStackTrace();
	}

	this.currentTurn = gameData.charAt( 0 ) - 48;

	if(!( ( this.currentTurn == 1) || ( this.currentTurn == 2 ) ) ) 
	{
	    System.out.println("Problems!\n the current turn read is not a " +
			       "1 or a 2!");
	    this.exit_function( 0 );
	} 
	else if ( this.getCurrentTurn() != this.currentTurn ) 
	{
	    System.out.println("Problems!\n the current turn read does not " +
			       "correspond to the number of pieces played!");
	    this.exit_function( 0 );			
	}
    } // end GameBoard( String )

	
   
    public GameBoard( int masterGame[][] ) 
    {
	
	this.playBoard = new int[6][7];
	this.pieceCount = 0;

	for( int i = 0; i < 6; i++ ) 
	{
	    for( int j = 0; j < 7; j++) 
	    {
		this.playBoard[ i ][ j ] = masterGame[ i ][ j ];
		
		if( this.playBoard[i][j] > 0 )
		{
		    this.pieceCount++;
		}
	    }
	}
    }

   
    public int getScore( int player ) 
    {
	//reset the scores
	int playerScore = 0;

	//check horizontally
	for( int i = 0; i < 6; i++ ) 
        {
	    for( int j = 0; j < 4; j++ ) 
	    {
		if( ( this.playBoard[ i ][j] == player ) &&
		    ( this.playBoard[ i ][ j+1 ] == player ) &&
		    ( this.playBoard[ i ][ j+2 ] == player ) &&
		    ( this.playBoard[ i ][ j+3 ] == player ) ) 
		{
		    playerScore++;
		}
	    }
	} // end horizontal

	//check vertically
	for( int i = 0; i < 3; i++ ) {
	    for( int j = 0; j < 7; j++ ) {
		if( ( this.playBoard[ i ][ j ] == player ) &&
		    ( this.playBoard[ i+1 ][ j ] == player ) &&
		    ( this.playBoard[ i+2 ][ j ] == player ) &&
		    ( this.playBoard[ i+3 ][ j ] == player ) ) {
		    playerScore++;
		}
	    }
	} // end verticle
	
	//check diagonally - backs lash ->	\
	    for( int i = 0; i < 3; i++ ){
		for( int j = 0; j < 4; j++ ) {
		    if( ( this.playBoard[ i ][ j ] == player ) &&
			( this.playBoard[ i+1 ][ j+1 ] == player ) &&
			( this.playBoard[ i+2 ][ j+2 ] == player ) &&
			( this.playBoard[ i+3 ][ j+3 ] == player ) ) {
			playerScore++;
		    }
		}
	    }
	    
	    //check diagonally - forward slash -> /
	    for( int i = 0; i < 3; i++ ){
		for( int j = 0; j < 4; j++ ) {
		    if( ( this.playBoard[ i+3 ][ j ] == player ) &&
			( this.playBoard[ i+2 ][ j+1 ] == player ) &&
			( this.playBoard[ i+1 ][ j+2 ] == player ) &&
			( this.playBoard[ i ][ j+3 ] == player ) ) {
			playerScore++;
		    }
		}
	    }// end player score check
	    
	    return playerScore;
    } // end getScore

   
    public int getCurrentTurn() 
    {
	return ( this.pieceCount % 2 ) + 1 ;
    } // end getCurrentTurn


    
    public int getPieceCount() 
    {
	return this.pieceCount;
    }

    
    public int[][] getGameBoard() 
    {
	return this.playBoard;
    }

    
    public boolean isValidPlay( int column ) {
	
	if ( !( column >= 0 && column <= 7 ) ) {
	    // check the column bounds
	    return false;
	} else if( this.playBoard[0][ column ] > 0 ) {
	    // check if column is full
	    return false;
	} else {
	    // column is NOT full and the column is within bounds
	    return true;
	}
    }

    
    public boolean playPiece( int column ) {
	
	// check if the column choice is a valid play
	if( !this.isValidPlay( column ) ) {
	    return false;
	} else {
	    
	    //starting at the bottom of the board,
	    //place the piece into the first empty spot
	    for( int i = 5; i >= 0; i-- ) {
		if( this.playBoard[i][column] == 0 ) {
		    if( this.pieceCount % 2 == 0 ){
			this.playBoard[i][column] = 1;
			this.pieceCount++;
			
		    } else { 
			this.playBoard[i][column] = 2;
			this.pieceCount++;
		    }
		    
		   	    
		    return true;
		}
	    }
	    //the pgm shouldn't get here
	    System.out.println("Something went wrong with playPiece()");
	    
	    return false;
	}
    } //end playPiece
    
    
    public void removePiece( int column ) {
	
	// starting looking at the top of the game board,
	// and remove the top piece
	for( int i = 0; i < 6; i++ ) {
	    if( this.playBoard[ i ][ column ] > 0 ) {
		this.playBoard[ i ][ column ] = 0;
		this.pieceCount--;
		
		break;
	    }
	}
	
		
    } // end remove piece	
    
   
    public void printGameBoard() 
    {
	System.out.println(" -----------------");
	System.out.println(" | C O L U M N S | ");
	System.out.println(" | 1 2 3 4 5 6 7 | ");
	System.out.println(" -----------------");

	for( int i = 0; i < 6; i++ ) 
        {
	    System.out.print(" | ");
	    for( int j = 0; j < 7; j++ ) 
            {
                System.out.print( this.playBoard[i][j] + " " );
            }

	    System.out.println("| ");
	}
	
	System.out.println(" -----------------");
	System.out.println( "Score: Player 1 = " + getScore( 1 ) +
            ", Player2 = " + getScore( 2 ) + "\n " );
	
    } // end printGameBoard
    
    
    public void printGameBoardToFile( String outputFile ) {
	try {
	    BufferedWriter output = new BufferedWriter(
						       new FileWriter( outputFile ) );
	    
	    for( int i = 0; i < 6; i++ ) {
		for( int j = 0; j < 7; j++ ) {
		    output.write( this.playBoard[i][j] + 48 );
		}
		output.write("\r\n");
	    }
	    
	    //write the current turn
	    output.write( this.getCurrentTurn() + "\r\n");
	    output.close();
	    
	} catch( IOException e ) {
	    System.out.println("\nProblem writing to the output file!\n" +
			       "Try again.");
	    e.printStackTrace();
	}
    } // end printGameBoardToFile()
    
    private void exit_function( int value ){
	System.out.println("exiting from GameBoard.java!\n\n");
	System.exit( value );
    }
    
}  // end GameBoard class
