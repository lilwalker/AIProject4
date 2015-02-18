package AIProject4;
import java.util.ArrayList;


public class Bag {
	
	public String letter;
	public Integer capacity;
	public ArrayList<Item> contents;
	
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
	
	public void addItem(Item item){
		this.contents.add(item);
	}
	
	public void removeItem(Item item){
		for (int i = 0; i < contents.size(); i++){
			if (contents.get(i).letter.equals(item.letter)){
				contents.remove(i);
				return;
			}
		}
	}

}
