package AIProject4.constraints;

import java.util.ArrayList;

import AIProject4.Item;
import AIProject4.State;

public interface Constraint {
	public boolean satisfies(State state);	
}
