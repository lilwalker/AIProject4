package AIProject4;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CMatrix {
	
	Map<String,Map<String,Integer>> matrix;
	
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
		this.matrix = new HashMap<String,Map<String,Integer>>();
		// Iterate through matrix to make every cell 1 (default value)
		// Also initializes hashmaps
		for (int x = 0; x < bags.size(); x++){
			this.matrix.put(bags.get(x).letter, new HashMap<String,Integer>());
			for (int y = 0; y < items.size(); y++){
				this.matrix.get(bags.get(x).letter).put(items.get(y).letter, 1);
				
			}
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
				// Since we are ruling out, we should only be setting 0s;
				if (foundbag == 0) {
					matrix.get(bags.get(i).letter).put(parts[0], foundbag);
				}
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
				// Since we are ruling out, we should only be setting zeros
				if (foundbag == 0) {
					matrix.get(bags.get(i).letter).put(parts[0], foundbag);
				}
			}
		}
		
		printHashMap(matrix);
	}
	
	public void printHashMap(Map<String,Map<String, Integer>> map) {
		System.out.print("##");
		for (String bag : map.keySet()) {
			System.out.print("  " + bag);
		}
		System.out.println();
		
		for (String item : map.get(map.keySet().toArray()[0]).keySet()) {
			System.out.print(item + " ");
			
			for (String bag : map.keySet()) {
				System.out.print("  " + map.get(bag).get(item));
			}
			System.out.println();
		}
	}
	

}
