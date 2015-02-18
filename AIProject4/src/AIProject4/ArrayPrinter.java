package AIProject4;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * This is just used to print stuff out conveniently
 */


public class ArrayPrinter {

	public ArrayPrinter(int[][] board){
		for (int y = 0; y< board.length;y++){
			for (int x = 0; x<board[0].length; x++){
				System.out.print(board[x][y]+" ");
			}
			System.out.println();
		}
	}
	
	public ArrayPrinter(int[][] board, HashMap itemindex, HashMap bagindex){
		System.out.println("y" + board.length + "x"+board[0].length);
		for (int x = 0; x< board.length;x++){
			for (int y = 0; y<board[0].length; y++){
				System.out.println(board[x][y]+"   "+x+"   "+y);
			}
			//System.out.println();
		}
	}
	
	public ArrayPrinter(ArrayList<Assignment> assignments){
		System.out.println("Assignments:");
		for (Assignment assignment:assignments){
			System.out.println(assignment.item.letter+"  "+assignment.bag.letter);
		}
	}
	
}
