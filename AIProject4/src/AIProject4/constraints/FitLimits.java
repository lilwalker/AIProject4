package AIProject4.constraints;

import java.util.ArrayList;

import AIProject4.Bag;
import AIProject4.Item;
import AIProject4.State;

public class FitLimits implements Constraint {
	int lower,higher;
	public FitLimits(int lower, int higher) {
		this.lower = lower;
		this.higher = higher;
	}
	
	@Override
	public boolean satisfies(State state) {
		if (!allAssigned(state)) return true;
		for (Bag bag : state.getBags()) {
			if (bag.contents.size() < lower) return false;
			if (bag.contents.size() > higher) return false;
		}
		return true;
	}

	private boolean allAssigned(State state) {
		ArrayList<String> assigned = new ArrayList<String>();
		for (Bag bag : state.getBags()) {
			for (Item item : bag.contents)
				assigned.add(item.letter);
		}
		return (assigned.size()==state.getItems().size());
	}
}
