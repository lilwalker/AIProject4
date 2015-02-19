package AIProject4;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import AIProject4.constraints.Constraint;


public class MinConflictsSearch {

	Collection<Constraint> constraints;
	Collection<Item> variables;
	Collection<Bag> values;
	Map<Item, Bag> assignments;
	
	public MinConflictsSearch(Collection<Constraint> constraints, Collection<Item> variables, Collection<Bag> values) {
		this.constraints = constraints;
		this.variables = variables;
		this.values = values;
		this.assignments = new HashMap<Item,Bag>();
		Bag defaultBag = this.values.iterator().next();
		for (Item item : this.variables) {
			this.assignments.put(item, defaultBag);
		}
	}
	
	private State genState() {
		return null;
	}
	
	public int numConflicts() {
		int numConflicts = 0;
		for (Constraint constraint : constraints) {
			
		}
		return 0;
	}
}
