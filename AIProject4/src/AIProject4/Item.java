package AIProject4;

import java.util.ArrayList;

public class Item {
	
	public String letter;
	public Integer weight;
	public Bag bag;
	public ArrayList<Bag> domain;
	
	public Item (String letter, int weight, Bag bag){
		this.letter = letter;
		this.weight = weight;
		this.bag = bag;
		this.domain = new ArrayList<Bag>();
	}
	
	public Item (String letter, int weight){
		this.letter = letter;
		this.weight = weight;
		//this.bag = null;
	}

}
