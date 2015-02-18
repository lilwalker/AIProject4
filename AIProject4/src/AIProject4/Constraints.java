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
	//need variables for binary equals, binary not-equals, mutual exclusive
	
	
	//constructor just inits arraylists
	public Constraints(){
		this.items = new ArrayList<Item>();
		this.bags = new ArrayList<Bag>();
		this.constraints = new ArrayList<Constraint>();
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
		this.constraints.add(new Unary.Inclusive(parts[0], Arrays.asList(parts).subList(1, parts.length - 1)));
	}
	
	public void addUnaryEx(String string) {
		String[] parts = string.split(" ");
		this.constraints.add(new Unary.Exclusive(parts[0], Arrays.asList(parts).subList(1, parts.length - 1)));
	}
	
	public void addBinaryEq(String string) {
		String[] parts = string.split(" ");
		this.constraints.add(new Binary.Equals(parts[0], parts[1]));
	}
	
	public void addBinaryNotEq(String string) {
		String[] parts = string.split(" ");
		this.constraints.add(new Binary.NotEquals(parts[0], parts[1]));
	}

	public void addMutex(String string) {
		String[] parts = string.split(" ");
		this.constraints.add(new MutEx(parts[0], parts[1], parts[2], parts[3]));
	}
}
