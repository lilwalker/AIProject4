package AIProject4.constraints;

import java.util.Map;

import AIProject4.Bag;
import AIProject4.Item;
import AIProject4.State;

public interface Constraint {
	/**
	 * Used by backtracking
	 * Has some special conditions in case not all items are placed (doesn't check minimums for num items in bags etc.)
	 * @param state
	 * @return
	 */
	public boolean satisfies(State state);
	/**
	 * Used by min conflicts
	 * Does not have special conditions, strictly evaluates if the constraint is not met by this item
	 * @param variable
	 * @param assignments
	 * @return
	 */
	public boolean satisfies(Item variable, Map<Item, Bag> assignments);
}
