import java.util.ArrayList;


public class Bag {
	
	String letter;
	Integer capacity;
	ArrayList<Item> contents;
	
	public Bag(String letter, int capacity, ArrayList<Item> contents){
		this.letter = letter;
		this.capacity = capacity;
		this.contents = contents;
	}
	
	public Bag(String letter, int capacity){
		this.letter = letter;
		this.capacity = capacity;
		this.contents = new ArrayList<Item>();
	}

}
