import java.util.ArrayList;


public class Bag {
	
	char letter;
	Integer capacity;
	ArrayList<Item> contents;
	
	public Bag(char letter, int capacity, ArrayList<Item> contents){
		this.letter = letter;
		this.capacity = capacity;
		this.contents = contents;
	}
	
	public Bag(char letter, int capacity){
		this.letter = letter;
		this.capacity = capacity;
		this.contents = new ArrayList<Item>();
	}

}
