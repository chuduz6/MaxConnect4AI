import java.util.*;

/*
 * @author Chudamani Aryal
 */

public class AiPlayer 
{
    public GameBoard currentGame;
    final int NUM_COLS = 7;
	final int NUM_ROWS = 6;
	final int maxSlot=NUM_COLS*NUM_ROWS;
    
    public AiPlayer() 
    {
	
    }

    public int findBestPlay(GameBoard currentGame, boolean player1, int depthLeft, int current_player) 
    {	
    	int playChoice=99;    	
    	playChoice = bestMove(currentGame, player1, depthLeft, current_player);
    	while( !currentGame.isValidPlay( playChoice ) )
    	{
    		playChoice = bestMove(currentGame, player1, depthLeft, current_player);
    	}
	
	return playChoice;
    }
    
    public int bestMove(GameBoard currentGame, boolean player1, int depthLeft, int current_player)
    {
    	int bestScore=0;
    	int bestIndex=0;
    	if(player1)
    		bestScore=Integer.MIN_VALUE;
    	else
    		bestScore=Integer.MAX_VALUE;
    	
    	 for(int j = 0; j < NUM_COLS; j++)
         {
             if(currentGame.isValidPlay(j))
             {
                   currentGame.playPiece(j);
                   int score = alphaBetaPruning(depthLeft, currentGame, !player1, Integer.MIN_VALUE, Integer.MAX_VALUE, current_player);
                   if (player1)
                   {
                	   if(score>bestScore)
                	   {
                		   bestScore=score;
                		   bestIndex=j;
                	   }
                	                   	   
                   }
                   else if (!player1)
                   {
                	   if (score<bestScore)
                	   {
                		   bestScore=score;
                		   bestIndex=j;
                	   }
                   }
                   currentGame.removePiece(j);

             }
         }
    	 return bestIndex;
    }
    
    
    public int alphaBetaPruning(int depthLeft, GameBoard currentGame, boolean player1, int alpha, int beta, int current_player)
    {
    	if((currentGame.pieceCount==maxSlot) || (depthLeft==0))
    		return evaulationFunction(currentGame, current_player);
    	if(player1)
    	{
	    	for(int j = 0; j < NUM_COLS; j++)
	        {
	            if(currentGame.isValidPlay(j))
	            {
	                 currentGame.playPiece(j);
	                 int score = alphaBetaPruning(depthLeft-1, currentGame, !player1, alpha, beta, current_player);
	                 if (score > alpha)
	                 {
	                	 alpha=score;
	                	 if (alpha>=beta)
	                	 {
	                		 currentGame.removePiece(j);
	                		 break;
	                	 }
	                 }
	                  currentGame.removePiece(j);
	
	            }
	        }
    	}
    	
    	else if(!player1)
    	{
	    	for(int j = 0; j < NUM_COLS; j++)
	        {
	            if(currentGame.isValidPlay(j))
	            {
	                 currentGame.playPiece(j);
	                 int score = alphaBetaPruning(depthLeft-1, currentGame, !player1, alpha, beta, current_player);
	                 if (score < beta)
	                 {
	                	 beta=score;
	                	 if (beta<=alpha)
	                	 {
	                		 currentGame.removePiece(j);
	                		 break;
	                	 }
	                 }
	                  currentGame.removePiece(j);
	
	            }
	        }
    	}
    	
    	if(player1)
    	{
    		return alpha;
    	}
    	else
    		return beta;
    	
    	
    }
    
    public int fourFill(GameBoard currentGame, int player, int a1, int a2, int a3, int a4, int b1, int b2, int b3, int b4)
    {
    	
    	int tempScore=0;
    	if( ( currentGame.playBoard[ a1 ][b1] == player ) &&
    		    ( currentGame.playBoard[ a2 ][ b2 ] == player ) &&
    		    ( currentGame.playBoard[ a3 ][ b3 ] == player ) &&
    		    ( currentGame.playBoard[ a4 ][ b4 ] == player ) ) 
    		{
    			tempScore+=10000000;
    		}    		
    		
    	return tempScore;
    }
    
   
    
    public int threeFillOneBlank(GameBoard currentGame, int player, int a1, int a2, int a3, int a4, int b1, int b2, int b3, int b4)
    {
    	int tempScore=0;
    	if( ( currentGame.playBoard[ a1 ][b1] == player ) &&
    		    ( currentGame.playBoard[ a2 ][ b2 ] == player ) &&
    		    ( currentGame.playBoard[ a3 ][ b3 ] == player ) &&
    		    ( currentGame.playBoard[ a4 ][ b4 ] == 0 ) ) 
    		{
    			tempScore+=100000;
    		}
    		
    		else if( ( currentGame.playBoard[ a1 ][b1] == player ) &&
        		    ( currentGame.playBoard[ a2 ][ b2 ] == player ) &&
        		    ( currentGame.playBoard[ a3 ][ b3 ] == 0 ) &&
        		    ( currentGame.playBoard[ a4 ][ b4 ] == player ) ) 
        		{
    				tempScore+=100000;
        		}
    		
    		else if( ( currentGame.playBoard[ a1 ][b1] == player ) &&
        		    ( currentGame.playBoard[ a2 ][ b2 ] == 0 ) &&
        		    ( currentGame.playBoard[ a3 ][ b3 ] == player ) &&
        		    ( currentGame.playBoard[ a4 ][ b4 ] == player ) ) 
        		{
        		    tempScore+=100000;
        		}
    		else if( ( currentGame.playBoard[ a1 ][b1] == 0 ) &&
        		    ( currentGame.playBoard[ a2 ][ b2 ] == player ) &&
        		    ( currentGame.playBoard[ a3 ][ b3 ] == player ) &&
        		    ( currentGame.playBoard[ a4 ][ b4 ] == player ) ) 
        		{
        		    tempScore+=100000;
        		}
    	return tempScore;
    }
    
    public int twoFillTwoBlank(GameBoard currentGame, int player, int a1, int a2, int a3, int a4, int b1, int b2, int b3, int b4)
    {
    	int tempScore=0;
    	if( ( currentGame.playBoard[ a1 ][b1] == player ) &&
    		    ( currentGame.playBoard[ a2 ][ b2 ] == player ) &&
    		    ( currentGame.playBoard[ a3 ][ b3 ] == 0 ) &&
    		    ( currentGame.playBoard[ a4 ][ b4 ] == 0 ) ) 
    		{
				tempScore+=1000;
    		}
    	else if( ( currentGame.playBoard[ a1 ][b1] == player ) &&
    		    ( currentGame.playBoard[ a2 ][ b2 ] == 0 ) &&
    		    ( currentGame.playBoard[ a3 ][ b3 ] == player ) &&
    		    ( currentGame.playBoard[ a4 ][ b4 ] == 0 ) ) 
    		{
				tempScore+=1000;
    		}
    	else if( ( currentGame.playBoard[ a1 ][b1] == player ) &&
    		    ( currentGame.playBoard[ a2 ][ b2 ] == 0 ) &&
    		    ( currentGame.playBoard[ a3 ][ b3 ] == 0 ) &&
    		    ( currentGame.playBoard[ a4 ][ b4 ] == player ) ) 
    		{
				tempScore+=1000;
    		}
    	else if( ( currentGame.playBoard[ a1 ][b1] == 0 ) &&
        		    ( currentGame.playBoard[ a2 ][ b2 ] == player ) &&
        		    ( currentGame.playBoard[ a3 ][ b3 ] == player ) &&
        		    ( currentGame.playBoard[ a4 ][ b4 ] == 0 ) ) 
        		{
    				tempScore+=1000;
        		}
    	else if( ( currentGame.playBoard[ a1 ][b1] == 0 ) &&
    		    ( currentGame.playBoard[ a2 ][ b2 ] == 0 ) &&
    		    ( currentGame.playBoard[ a3 ][ b3 ] == player ) &&
    		    ( currentGame.playBoard[ a4 ][ b4 ] == player ) ) 
    		{
				tempScore+=1000;
    		}
    	else if( ( currentGame.playBoard[ a1 ][b1] == 0 ) &&
    		    ( currentGame.playBoard[ a2 ][ b2 ] == player ) &&
    		    ( currentGame.playBoard[ a3 ][ b3 ] == 0 ) &&
    		    ( currentGame.playBoard[ a4 ][ b4 ] == player ) ) 
    		{
				tempScore+=1000;
    		}
    	return tempScore;
    }
    
    public int oneFillThreeBlank(GameBoard currentGame, int player, int a1, int a2, int a3, int a4, int b1, int b2, int b3, int b4)
    {
    	int tempScore=0;
    	if( ( currentGame.playBoard[ a1 ][b1] == player ) &&
    		    ( currentGame.playBoard[ a2 ][ b2 ] == 0 ) &&
    		    ( currentGame.playBoard[ a3 ][ b3 ] == 0 ) &&
    		    ( currentGame.playBoard[ a4 ][ b4 ] == 0 ) ) 
    		{
				tempScore+=1;
    		}
    	else if( ( currentGame.playBoard[ a1 ][b1] == 0 ) &&
    		    ( currentGame.playBoard[ a2 ][ b2 ] == player ) &&
    		    ( currentGame.playBoard[ a3 ][ b3 ] == 0 ) &&
    		    ( currentGame.playBoard[ a4 ][ b4 ] == 0 ) ) 
    		{
				tempScore+=1;
    		}
    	else if( ( currentGame.playBoard[ a1 ][b1] == 0 ) &&
    		    ( currentGame.playBoard[ a2 ][ b2 ] == 0 ) &&
    		    ( currentGame.playBoard[ a3 ][ b3 ] == player ) &&
    		    ( currentGame.playBoard[ a4 ][ b4 ] == 0 ) ) 
    		{
				tempScore+=1;
    		}
    	else if( ( currentGame.playBoard[ a1 ][b1] == 0 ) &&
    		    ( currentGame.playBoard[ a2 ][ b2 ] == 0 ) &&
    		    ( currentGame.playBoard[ a3 ][ b3 ] == 0 ) &&
    		    ( currentGame.playBoard[ a4 ][ b4 ] == player ) ) 
    		{
				tempScore+=1;
    		}
    	return tempScore;
    }
    
    public int getEvaulationScore(GameBoard currentGame, int player)
    {
    	int playerScore=0;
    	//check horizontally
    	for( int i = 0; i < 6; i++ ) 
            {
    	    for( int j = 0; j < 4; j++ ) 
    	    {
    	    	playerScore = playerScore + fourFill(currentGame, player, i, i, i, i, j, j+1, j+2, j+3); 
    	    	playerScore = playerScore + threeFillOneBlank(currentGame, player, i, i, i, i, j, j+1, j+2, j+3); 
    	    	playerScore = playerScore + twoFillTwoBlank(currentGame, player, i, i, i, i, j, j+1, j+2, j+3);
    	    	playerScore = playerScore + oneFillThreeBlank(currentGame, player, i, i, i, i, j, j+1, j+2, j+3);      		
    	    }
    	} // end horizontal

    	//check vertically
    	for( int i = 0; i < 3; i++ ) {
    	    for( int j = 0; j < 7; j++ ) {
    	    	playerScore = playerScore + fourFill(currentGame, player, i, i+1, i+2, i+3, j, j, j, j); 
    	    	playerScore = playerScore + threeFillOneBlank(currentGame, player, i, i+1, i+2, i+3, j, j, j, j); 
    	    	playerScore = playerScore + twoFillTwoBlank(currentGame, player, i, i+1, i+2, i+3, j, j, j, j); 
    	    	playerScore = playerScore + oneFillThreeBlank(currentGame, player, i, i+1, i+2, i+3, j, j, j, j); 
    	    	
    		}
    	} // end verticle
    	
    	//check diagonally - backs lash ->	\
    	    for( int i = 0; i < 3; i++ ){
    		for( int j = 0; j < 4; j++ ) {
    			playerScore = playerScore + fourFill(currentGame, player, i, i+1, i+2, i+3, j, j+1, j+2, j+3); 
    			playerScore = playerScore + threeFillOneBlank(currentGame, player, i, i+1, i+2, i+3, j, j+1, j+2, j+3); 
    	    	playerScore = playerScore + twoFillTwoBlank(currentGame, player, i, i+1, i+2, i+3, j, j+1, j+2, j+3); 
    	    	playerScore = playerScore + oneFillThreeBlank(currentGame, player, i, i+1, i+2, i+3, j, j+1, j+2, j+3); 
    		   
    		}
    	    }
    	    
    	    //check diagonally - forward slash -> /
    	    for( int i = 0; i < 3; i++ ){
    		for( int j = 0; j < 4; j++ ) {
    			playerScore = playerScore + fourFill(currentGame, player, i+3, i+2, i+1, i, j, j+1, j+2, j+3);
    			playerScore = playerScore + threeFillOneBlank(currentGame, player, i+3, i+2, i+1, i, j, j+1, j+2, j+3); 
    	    	playerScore = playerScore + twoFillTwoBlank(currentGame, player, i+3, i+2, i+1, i, j, j+1, j+2, j+3); 
    	    	playerScore = playerScore + oneFillThreeBlank(currentGame, player, i+3, i+2, i+1, i, j, j+1, j+2, j+3); 
    	    	
    		  }
    	    }
    	return playerScore;
    }
    
    public int evaulationFunction(GameBoard currentGame, int current_player)
    {
    	int finalScore, scoreA, scoreB;
    	if(current_player==1)
    	{
    		scoreA=getEvaulationScore(currentGame, 1);
        	scoreB=getEvaulationScore(currentGame, 2);
        	
    	}
    	
    	else
    	{
    		scoreA=getEvaulationScore(currentGame, 2);
        	scoreB=getEvaulationScore(currentGame, 1);
        	
    	}
    	
    
    	finalScore=scoreA-scoreB;
    	return finalScore;
    	
    }
}
