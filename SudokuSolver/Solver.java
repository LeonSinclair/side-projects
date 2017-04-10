import java.util.Arrays;


public class Solver {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] grid =  new int[9][9];
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				grid[i][j] = 0; //default unassigned value
			}
		}
		grid[0][0] = 2;
		grid[0][3] = 8;
		grid[1][3] = 1;
		grid[1][4] = 5;
		grid[1][7] = 6;
		grid[2][0] = 1;
		grid[2][1] = 8;
		grid[2][2] = 7;
		grid[2][3] = 6;
		grid[3][0] = 3;
		grid[3][5] = 5;
		grid[3][7] = 9;
		grid[4][2] = 9;
		grid[4][6] = 4;
		grid[5][1] = 4;
		grid[5][3] = 9;
		grid[5][8] = 1;
		grid[6][5] = 1;
		grid[6][6] = 2;
		grid[6][7] = 3;
		grid[6][8] = 4;
		grid[7][1] = 3;
		grid[7][4] = 7;
		grid[7][5] = 4;
		grid[8][5] = 6;
		grid[8][8] = 8;
		
		for(int i = 0; i < 9; i++)
		{
			//print out the test values
			System.out.println(Arrays.toString(grid[i]));
		}
		System.out.println(\n \n \n); //clear out the console
		
		backtrack(grid);
		for(int i = 0; i < 9; i++)
		{
			System.out.println(Arrays.toString(grid[i]));
		}
		
		

	}
	
	public static boolean backtrack(int[][] grid)
	{
		int row, col;
		if(unassignedExists(grid)[0]!=-1)
		{
			int[] tmp = unassignedExists(grid);
			row = tmp[0];
			col = tmp[1];
		}
		else return true;
		for (int num = 1; num <= 9; num++)
	    {
	        // if looks promising
	        if (validGrid(grid, row, col, num))
	        {
	            // make tentative assignment
	            grid[row][col] = num;
	 
	            // return, if success, yay!
	            if (backtrack(grid))
	                return true;
	 
	            // failure, unmake & try again
	            grid[row][col] = 0;
	        }
	    }
	    return false;
	}
	

	public static boolean validGrid(int[][] grid, int row, int col, int num)
	{
		for(int i = 0; i < 9; i++)
		{
			if(grid[i][col] == num) return false;
			else if(grid[row][i] == num) return false;
		}
		row = row - row%3;
		col = col - col%3;
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				if(grid[row+i][col+j] == num) return false;
			}
		}
		return true;
	}
	
	public static int[] unassignedExists(int[][]grid)
	{
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				if(grid[i][j]== 0) return new int[]{i,j};
			}
		}
		return new int[]{-1}; //fail to find
	}

}
