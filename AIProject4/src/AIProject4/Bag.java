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
	
	public int weight() {
		int i = 0;
		for (Item it : contents) {
			i += it.weight;
		}
		return i;
	}
	public void addItem(Item item){
		if (!inBag(item))
			this.contents.add(item);
	}
	
	private boolean inBag(Item item) {
		for (Item itemin : contents){
			if (itemin.letter.equals(item.letter))
				return true;
		}
		return false;
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
