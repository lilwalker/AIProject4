package AIProject4.constraints;

import java.util.Map;

import AIProject4.Bag;
import AIProject4.Item;
import AIProject4.State;

public interface Constraint {
	public boolean satisfies(State state);
	public boolean satisfies(Item variable, Map<Item, Bag> assignments);
}
