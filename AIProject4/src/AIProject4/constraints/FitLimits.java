package AIProject4.constraints;

import AIProject4.Bag;
import AIProject4.State;

public class FitLimits implements Constraint {
	int lower,higher;
	public FitLimits(int lower, int higher) {
		this.lower = lower;
		this.higher = higher;
	}
	
	@Override
	public boolean satisfies(State state) {
		for (Bag bag : state.getBags()) {
			if (bag.contents.size() < lower) return false;
			if (bag.contents.size() > higher) return false;
		}
		return true;
	}
}
