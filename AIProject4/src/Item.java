
public class Item {
	
	String letter;
	Integer weight;
	Bag bag;
	
	public Item (String letter, int weight, Bag bag){
		this.letter = letter;
		this.weight = weight;
		this.bag = bag;
	}
	
	public Item (String letter, int weight){
		this.letter = letter;
		this.weight = weight;
		//this.bag = null;
	}

}
