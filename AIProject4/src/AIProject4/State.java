package AIProject4;

import java.util.List;

public class State {
	private List<Bag> bags;
	private List<Item> items;
	
	State(List<Bag> bags){
		this.bags = bags;
	}
	
	State(List<Bag> bags, List<Item> items){
		this.bags = bags;
		this.items = items;
	}

	public List<Bag> getBags() {
		return bags;
	}
	
	public List<Item> getItems() {
		return items;
	}

	public void setBags(List<Bag> bags) {
		this.bags = bags;
	}
}
