package AIProject4;
import java.util.ArrayList;

/*
 * Holds the constraints
 * items are in an arraylist
 * bags are in an array list
 * the unary constraints (inc and ex) are in a constraint matrix
 * upper and lower limits are integers
 * */
public class Constraints {
	
	ArrayList<Item> items;
	ArrayList<Bag> bags;
	CMatrix cmatrix;
	int upperlimit;
	int lowerlimit;
	//need variables for binary equals, binary not-equals, mutual exclusive
	
	
	//constructor just inits arraylists
	public Constraints(){
		this.items = new ArrayList<Item>();
		this.bags = new ArrayList<Bag>();
	}
	
	/*
	 * change every string representation of an item from the file into an Item 
	 * and add to arraylist
	 */	
	public void addItems(String line){
		String[] parts = line.split(" ");
		String name = parts[0];
		int value = Integer.valueOf(parts[1]);
		items.add(new Item(name, value));
	}
	
	/*
	 * change every string representation of a bag from the file into a Bag
	 * and add to arraylist 
	 */	
	public void addBags(String line){
		String[] parts = line.split(" ");
		String name = parts[0];
		int value = Integer.valueOf(parts[1]);
		bags.add(new Bag(name, value));
	}

	//add the upper and lower limits
	public void addLimits(String string) {
		String[] parts = string.split(" ");
		lowerlimit = Integer.valueOf(parts[0]);
		upperlimit = Integer.valueOf(parts[1]);
	}

	//create the cmatrix using the unary constraints, items, and bags
	public void addCMatrix(ArrayList<String> inclusive,
			ArrayList<String> exclusive) {
		this.cmatrix = new CMatrix(items, bags, inclusive, exclusive);
		
		
	}

}
