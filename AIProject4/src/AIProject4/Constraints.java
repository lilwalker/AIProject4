package AIProject4;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import AIProject4.constraints.Binary;
import AIProject4.constraints.Constraint;
import AIProject4.constraints.FitLimits;
import AIProject4.constraints.MutEx;
import AIProject4.constraints.Unary;

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
	List<Constraint> constraints;
	List<Binary> binaryconstraints;
	List<MutEx> mutexconstraints;
	//need variables for binary equals, binary not-equals, mutual exclusive
	
	
	//constructor just inits arraylists
	public Constraints(){
		this.items = new ArrayList<Item>();
		this.bags = new ArrayList<Bag>();
		this.constraints = new ArrayList<Constraint>();
		this.binaryconstraints = new ArrayList<Binary>();
		this.mutexconstraints = new ArrayList<MutEx>();
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
		this.constraints.add(new FitLimits(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
	}

	public void addUnaryInc(String string) {
		String[] parts = string.split(" ");
		this.constraints.add(new Unary.Inclusive(parts[0], Arrays.asList(parts).subList(1, parts.length)));
	}
	
	public void addUnaryEx(String string) {
		String[] parts = string.split(" ");
		this.constraints.add(new Unary.Exclusive(parts[0], Arrays.asList(parts).subList(1, parts.length)));
	}
	
	public void addBinaryEq(String string) {
		String[] parts = string.split(" ");
		this.constraints.add(new Binary.Equals(parts[0], parts[1]));
		//this.binaryconstraints.add(new Binary.Equals(parts[0], parts[1]));
	}
	
	public void addBinaryNotEq(String string) {
		String[] parts = string.split(" ");
		this.constraints.add(new Binary.NotEquals(parts[0], parts[1]));
		//this.binaryconstraints.add(new Binary.NotEquals(parts[0], parts[1]));
	}

	public void addMutex(String string) {
		String[] parts = string.split(" ");
		this.constraints.add(new MutEx(parts[0], parts[1], parts[2], parts[3]));
		//this.mutexconstraints.add(new MutEx(parts[0], parts[1], parts[2], parts[3]));
	}
	
	public Boolean satisfiesAll(State state){
		for (Constraint constr: constraints){
			if (!constr.satisfies(state))
				return false;
		}
		for (Bag bag : state.getBags()){
			int currentweight = 0;
			for (Item item : bag.contents){
				currentweight += item.weight;
			}
			if (currentweight > bag.capacity)
				return false;
		}
		return true;
	}

	public ArrayList<Arc> getNeighbors(Item item) {
		ArrayList<Arc> arcs = new ArrayList<Arc>();
		for (int i = 0; i < binaryconstraints.size(); i++){
			if (binaryconstraints.get(i).getItem1().equals(item.letter)){
				arcs.add(new Arc(getItem(binaryconstraints.get(i).getItem1()),item));
			}
			if (binaryconstraints.get(i).getItem2().equals(item.letter)){
				arcs.add(new Arc(getItem(binaryconstraints.get(i).getItem2()),item));
			}
			
		}
		for (int i = 0; i < mutexconstraints.size(); i++){
			if (mutexconstraints.get(i).getItem1().equals(item.letter)){
				arcs.add(new Arc(getItem(mutexconstraints.get(i).getItem1()),item));
			}
			if (mutexconstraints.get(i).getItem2().equals(item.letter)){
				arcs.add(new Arc(getItem(mutexconstraints.get(i).getItem2()),item));
			}
		}
		return arcs;
	}

	private Item getItem(String item1) {
		for (Item item: items){
			if (item.letter.equals(item1))
				return item;
		}
		return null;
	}
}
