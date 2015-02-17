import java.util.ArrayList;


public class Constraints {
	
	ArrayList<Item> items;
	ArrayList<Bag> bags;
	CMatrix cmatrix;
	int upperlimit;
	int lowerlimit;
	//need variables for binary equals, binary not-equals, mutual exclusive
	
	public Constraints(){
		this.items = new ArrayList<Item>();
		this.bags = new ArrayList<Bag>();
	}
	
	public void addItems(String line){
		String[] parts = line.split(" ");
		String name = parts[0];
		int value = Integer.valueOf(parts[1]);
		items.add(new Item(name, value));
		
	}
	
	public void addBags(String line){
		String[] parts = line.split(" ");
		String name = parts[0];
		int value = Integer.valueOf(parts[1]);
		bags.add(new Bag(name, value));
	}

	public void addLimits(String string) {
		String[] parts = string.split(" ");
		lowerlimit = Integer.valueOf(parts[0]);
		upperlimit = Integer.valueOf(parts[1]);
	}

	public void addCMatrix(ArrayList<String> inclusive,
			ArrayList<String> exclusive) {
		this.cmatrix = new CMatrix(items, bags, inclusive, exclusive);
		
		
	}

}
