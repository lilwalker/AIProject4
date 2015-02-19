package AIProject4.constraints;

import java.util.Map;

import AIProject4.Bag;
import AIProject4.Item;
import AIProject4.State;

public class Capacity implements Constraint {

	@Override
	public boolean satisfies(State state) {
		for (Bag bag : state.getBags()) {
			int weight = 0;
			for (Item item : bag.contents) {
				weight += item.weight;
				if (weight > bag.capacity) return false;
			}
		}
		return true;
	}

	@Override
	public boolean satisfies(Item variable, Map<Item, Bag> assignments) {
		Bag bag = assignments.get(variable);
		
		return (bag.weight() <= bag.capacity) && (bag.weight() >= bag.capacity * .9);
	}
}
