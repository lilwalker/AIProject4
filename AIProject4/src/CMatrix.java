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
	
	public void MakeCMatrix(ArrayList<Item> items, ArrayList<Bag> bags, ArrayList<String> inclusive,
			ArrayList<String> exclusive){
		this.constmatrix = new int[items.size()][bags.size()];
		for (int x = 0; x < items.size(); x++){
			for (int y = 0; y < bags.size(); y++){
				constmatrix[x][y] = 1;
			}
		}
		this.itemindex = new HashMap<String, Integer>();
		this.bagindex = new HashMap<String, Integer>();
		for (int i = 0; i<items.size(); i++){
			//System.out.println(items.get(i).letter + "   "+ i);
			//System.out.println(items.size());
			itemindex.put(items.get(i).letter, i);
		}
		for (int b = 0; b<bags.size(); b++){
			System.out.println(bags.get(b).letter + "   "+ b);
			bagindex.put(bags.get(b).letter, b);
		}	
		for (int c = 0; c<inclusive.size(); c++){
			String[] parts = inclusive.get(c).split(" ");
			for (int i  = 0; i < bags.size(); i++){
				int foundbag = 0;
				for (int j = 1; j < parts.length; j++){
					if (bags.get(i).letter.equals(parts[j])){
						foundbag = 1;
					}
				}
				constmatrix[itemindex.get(parts[0])][bagindex.get(bags.get(i).letter)] = foundbag;
			}
		}
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
