package AIProject4;

import java.util.List;

public class State {
	private List<Bag> bags;
	
	State(List<Bag> bags){
		this.bags = bags;
	}

	public List<Bag> getBags() {
		return bags;
	}

	public void setBags(List<Bag> bags) {
		this.bags = bags;
	}
}
