import java.io.*;
import java.util.Scanner;

/**
 * 
 * @author Chudamani Aryal
*/

public class maxconnect4
{
  public static void main(String[] args) 
  {
    // check for the correct number of arguments
    if( args.length != 4 ) 
    {
      System.out.println("Four command-line arguments are needed:\n"
                         + "Usage: java [program name] interactive [input_file] [computer-next / human-next] [depth]\n"
                         + " or:  java [program name] one-move [input_file] [output_file] [depth]\n");

      exit_function( 0 );
     }
		
    // parse the input arguments
    String game_mode = args[0].toString();				// the game mode
    String input = args[1].toString();					// the input game file
    int depthLevel = Integer.parseInt( args[3] );  		// the depth level of the ai search
		
    // create and initialize the game board
    GameBoard currentGame = new GameBoard( input );
    
    // create the Ai Player
    AiPlayer calculon = new AiPlayer();
		
    //  variables to keep up with the game
    int playColumn = 99;				//  the players choice of column to play
    boolean playMade = false;			//  set to true once a play has been made
    int current_player;
    int human_choice;

    if( game_mode.equalsIgnoreCase( "interactive" ) ) 
    {
		String firstPlay=args[2].toString();
		Scanner sc=new Scanner(System.in);
		currentGame.printGameBoard();
		while(currentGame.pieceCount<42)
		{
			if(firstPlay.equalsIgnoreCase("human-next"))
			{
				
				current_player = currentGame.getCurrentTurn();
				System.out.println("HUMAN TURN");
				System.out.println("Enter the column number range [1,7]");
				human_choice=sc.nextInt()-1;
				while(!currentGame.isValidPlay(human_choice))
				{
					System.out.println("INVALID move! Enter the column number range [0,7]");
					human_choice=sc.nextInt()-1;
				}
				currentGame.playPiece( human_choice );        
		        System.out.println("move " + currentGame.getPieceCount() 
		                           + ": Player " + current_player + " Human "
		                           + ", column " + (human_choice+1));
		        System.out.print("game state after move:\n");
		        currentGame.printGameBoard();		        
		        currentGame.printGameBoardToFile("human.txt");
		        firstPlay="computer-next";
				
				
			}
			else if(firstPlay.equalsIgnoreCase("computer-next"))
			{
				current_player = currentGame.getCurrentTurn();
				playColumn = calculon.findBestPlay(currentGame, true, depthLevel, current_player);
		        currentGame.playPiece( playColumn );        
		        System.out.println("move " + currentGame.getPieceCount() 
		                           + ": Player " + current_player + " Computer "
		                           + ", column " + (playColumn+1));
		        System.out.print("game state after move:\n");
		        currentGame.printGameBoard();		        
		        currentGame.printGameBoardToFile("computer.txt" );
		        firstPlay="human-next";
			}
			
			else
			{
				System.out.println("Wrong player entered \n");
			      System.exit(0);
			}
		}		
		
		System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over");
		return;
    }   

    else if (game_mode.equalsIgnoreCase( "one-move" ))
    {	 
	    String output = args[2].toString();	    
	    System.out.print("\nMaxConnect-4 game\n");
	    System.out.print("game state before move:\n");
	    currentGame.printGameBoard();
	    
	    
	    if( currentGame.getPieceCount() < 42 ) 
	    {
	        current_player = currentGame.getCurrentTurn();
	        playColumn = calculon.findBestPlay(currentGame, true, depthLevel, current_player);
	        currentGame.playPiece( playColumn );        
	        System.out.println("move " + currentGame.getPieceCount() 
	                           + ": Player " + current_player
	                           + ", column " + playColumn);
	        System.out.print("game state after move:\n");
	        currentGame.printGameBoard();		        
	        currentGame.printGameBoardToFile( output );
	    } 
	    else 
	    {
	    	System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over");
	    }
		
	    			
    }
    
    else 
    {
      System.out.println( "\n" + game_mode + " is an unrecognized game mode \n try again. \n" );
      return;
    }
    return;
    
} // end of main()
	
 
  private static void exit_function( int value )
  {
      System.out.println("exiting from MaxConnectFour.java!\n\n");
      System.exit( value );
  }
  
} // end of class connectFour
