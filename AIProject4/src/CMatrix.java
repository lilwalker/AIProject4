import java.util.ArrayList;
import java.util.HashMap;


public class CMatrix {
	
	HashMap<String, Integer> itemindex;
	HashMap<String, Integer> bagindex;
	int[][] constmatrix;
	
	public CMatrix(ArrayList<Item> items, ArrayList<Bag> bags, ArrayList<String> inclusive,
			ArrayList<String> exclusive){
		
		MakeCMatrix(items, bags, inclusive, exclusive);
		
	}
	
	/* 		
	 * For items A, B, C, D, bags p,q,r, 
	 * Inc Unary Constrinats are: A p q, C r q
	 * Ex Unary Constrinats are: B r
	 * 
	 * Constraints Matrix:
	 * 		A	B	C	D
	 * p	1	1	0	1
	 * q	1	1	1	1
	 * r	0	0	1	1
	 */
	
	public void MakeCMatrix(ArrayList<Item> items, ArrayList<Bag> bags, ArrayList<String> inclusive,
			ArrayList<String> exclusive){
		// Constraint Matrix will have the dimentions items-by-bags
		this.constmatrix = new int[items.size()][bags.size()];
		// Iterate through matrix to make every cell 1 (default value)
		for (int x = 0; x < items.size(); x++){
			for (int y = 0; y < bags.size(); y++){
				constmatrix[x][y] = 1;
			}
		}
		
		// Init hashmaps tying a bag or item letter (key) to the index in the matrix (value)
		this.itemindex = new HashMap<String, Integer>();
		this.bagindex = new HashMap<String, Integer>();
		for (int i = 0; i<items.size(); i++){
			itemindex.put(items.get(i).letter, i);
		}
		for (int b = 0; b<bags.size(); b++){
			System.out.println(bags.get(b).letter + "   "+ b);
			bagindex.put(bags.get(b).letter, b);
		}	
		
		/* 
		 * Apply Inclusive constraints. For each constraint, iterate through the bags, then 
		 * through the bags specified in the constraint. If a bag matched a bag in the constraint,
		 * the cell corresponding to that bag and the item in the constraint is given a value of 
		 * 1. Else the cell is set to 0, because the assignment cannot be made.
		 */
		for (int c = 0; c<inclusive.size(); c++){
			// split the constraint by spaces. "C p q" becomes the String array {C,p,q}
			String[] parts = inclusive.get(c).split(" ");
			// iterate through bags
			for (int i  = 0; i < bags.size(); i++){
				//if the bag is not found, 
				int foundbag = 0;
				for (int j = 1; j < parts.length; j++){
					if (bags.get(i).letter.equals(parts[j])){
						foundbag = 1;
					}
				}
				constmatrix[itemindex.get(parts[0])][bagindex.get(bags.get(i).letter)] = foundbag;
			}
		}
		
		/* 
		 * Apply Exclusive constraints. For each constraint, iterate through the bags, then 
		 * through the bags specified in the constraint. If a bag matched a bag in the constraint,
		 * the cell corresponding to that bag and the item in the constraint is given a value of 
		 * 0, because the assignment cannot be made. Else the cell is set to 1.
		 */
		for (int c = 0; c<exclusive.size(); c++){
			String[] parts = exclusive.get(c).split(" ");
			for (int i  = 0; i < bags.size(); i++){
				int foundbag = 1;
				for (int j = 1; j < parts.length; j++){
					if (bags.get(i).letter.equals(parts[j])){
						foundbag = 0;
					}
				}
				constmatrix[itemindex.get(parts[0])][bagindex.get(bags.get(i).letter)] = foundbag;
			}
		}
		
		System.out.println(itemindex);
		System.out.println(bagindex);
		//System.out.println(constmatrix);
		new ArrayPrinter(constmatrix, itemindex, bagindex);
		
	}
	

}
