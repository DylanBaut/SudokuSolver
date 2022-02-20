public class SudokuSolver{


    private static final int GRID_SIZE = 9;
    private static int MISSES=0; 
    private static int EMPTY_SPACES=0;

    //main method: holds board 2D array and calls on methods to print the board and its difficulty
    public static void main(String[] args){

        int[][] board ={
            {0,0,2,6,0,0,5,0,7},
            {0,8,0,4,3,2,0,6,1},
            {0,0,6,1,5,0,8,0,0},
            {0,9,0,0,0,0,0,0,6},
            {0,2,4,0,7,3,0,0,0},
            {0,0,0,8,0,4,3,0,2},
            {0,5,9,7,8,0,0,1,3},
            {0,5,9,7,8,0,0,1,3},
            {8,0,1,3,4,0,0,7,0}
        };

        if (fillBoard(board)==true){
            System.out.println("Solved successfully: difficulty of "+computeDifficulty() +" difficulty points out of 8.");
        }else{
            System.out.println("ERROR: Unsolvable board.");
        }
        printBoard(board);
    }
    //checks if number is in row: loops through each column within specified row 
    //and returns true if number is matched, false if not

    private static boolean isNumberInRow(int[][] board, int number, int row){
        for( int i=0; i< GRID_SIZE; i++){
            if(board[row][i]== number)
            return true;
        }
        return false;
    }

    //checks if number is in col: loops through each row within specified col 
    //and returns true if number is matched, false if not
    
    private static boolean isNumberInCol(int[][] board, int number, int col){
        for( int i=0; i< GRID_SIZE; i++){
            if(board[i][col]== number)
            return true;
        }
        return false;
    }

    //checks is number is within 3x3 box
    //finds relative position in box by subtracting the modulus of 3 from the col/row
    //This ends up being the top left corner, from which we start looping through 3 positions
    //in both the row and column dimensions. Returns true if number is matched, false if not

    private static boolean boxChecker(int [][] board, int number, int row, int col){
        int boxRow= row - row%3;
        int boxCol= col - col%3;

        for (int i= boxRow; i<boxRow+3; i++){
            for (int j= boxCol; j<boxCol+3; j++){
        
                if (board[i][j]==number){
                    return true;
                }
            }
        }
        return false;
    }

    //uses boolean values returned by three prior methods to see if number
    //is viable in that spot, returns true if they all return false,
    //false in any other case

    private static boolean canPlaceHere(int[][] board, int number, int row, int col){
        if (!isNumberInCol(board, number,col)&& !isNumberInRow(board, number, row)&& boxChecker(board, number, row, col)){
            return true;
        }else{
            return false;
        }
    }

    //Algorithm for traversing and replacing squares with numbers;
    // loops through each row and column up to GRID_SIZE units in each direction
    // when value is 0, a for-loop will iterate up to 9 times for 1-9 digits
    // each attempt will be passed to canPlaceHere method to check viability
    // and if it returns true, the number will be placed in spot, otherwise it will be
    // set back to 0. Returns true if and when board is completed, and false if 
    // all 9 digits are cycled through and none work

    private static boolean fillBoard(int[][] board){
        for(int row =0; row<GRID_SIZE; row++){
             for(int col =0; col<GRID_SIZE; col++){
                 if(board[row][col]==0){
                     EMPTY_SPACES++;
                     for(int attempt=1; attempt<=GRID_SIZE; attempt++){
                        if(canPlaceHere(board, attempt, row, col)){
                            board[row][col]=attempt;

                            if(fillBoard(board)==true){
                                return true;
                            }else{
                                MISSES++;
                                board[row][col]=0;
                            }
                        }
                     }
                     
                     return false;
                 }
            }
        }
        return true;
    }

//computes difficulty for concatonation
    private static int computeDifficulty(){
        return MISSES/EMPTY_SPACES; 
    }
//prints the board: will iterate through the row and column
// dimensions, each time checking if the 3rd count has been reached
// through a modulus, not including the zeroth character. When the
// third character is reached, it will print corresponding dividers
// Each value is printed from the array, and newline for each row

    private static void printBoard(int[][] board){
        for(int row =0; row < GRID_SIZE; row++){
                if(row%3==0 && row!=0){
                    System.out.println("-----------");
                }
                for(int col=0; col< GRID_SIZE; col++){
                    if(col%3==0 && col !=0){
                        System.out.print("|");
                    }
                    System.out.print(board[row][col]);
                }
                System.out.println();
            }
    }
}
