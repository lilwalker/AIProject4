package AIProject4.constraints;

import java.util.Map;

import AIProject4.Bag;
import AIProject4.Item;
import AIProject4.State;

public interface Constraint {
	/**
	 * Used by backtracking
	 * @param state
	 * @return
	 */
	public boolean satisfies(State state);
	/**
	 * Used by min conflicts
	 * @param variable
	 * @param assignments
	 * @return
	 */
	public boolean satisfies(Item variable, Map<Item, Bag> assignments);
}
