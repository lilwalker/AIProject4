package AIProject4;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import AIProject4.constraints.Constraint;

/**
 * Min-Conflicts search
 * @author khazhy
 *
 */
public class MinConflictsSearch {

	Collection<Constraint> constraints;
	List<Item> variables;
	List<Bag> values;
	Map<Item, Bag> assignments;
	Random r = new Random();
	List<Item> conflicts;
	
	/**
	 * Places items into bags putting into the most open bag at the time. All items will be assigned, even if there are conflicts
	 * @param constraints
	 * @param variables
	 * @param values
	 */
	public MinConflictsSearch(Collection<Constraint> constraints, Collection<Item> variables, Collection<Bag> values) {
		this.constraints = constraints;
		this.variables = new ArrayList<Item>(variables);
		this.values = new ArrayList<Bag>(values);
		this.assignments = new HashMap<Item,Bag>();
		
		
		// Greedy algorithm for placing items - always put the item in the bag with the most remaining capacity.
		for (Item item : this.variables) {
			Bag maxBag = this.values.iterator().next();
			int maxVal = maxBag.capacity - maxBag.weight();
			for (Bag bag : this.values) {
				int val = bag.capacity - bag.weight();
				if (val > maxVal) {
					maxVal = val;
					maxBag = bag;
				}
			}
			
			this.assignments.put(item, maxBag);
			maxBag.contents.add(item);	
		}
		genState();
	}
	
	/**
	 * Assign an item and regenerate the state
	 * @param b
	 * @param i
	 */
	private void assignItem(Bag b, Item i) {
		this.assignments.put(i,b);
		genState();
	}
	
	/**
	 * Set the bag contenst according to our assignments.
	 * State is used by the constraints
	 * @return
	 */
	private State genState() {
		for (Bag bag : this.values) {
			bag.contents = new ArrayList<Item>();
		}
		
		for (Item item : variables) {
			this.assignments.get(item).contents.add(item);
		}
		
		return new State(this.values, this.variables);
	}
	
	/**
	 * Finds the number of variables that are conflicting
	 * @return
	 */
	public int findConflicts() {
		this.conflicts = new ArrayList<Item>();
		for (Item item : variables) {
			if (numConflicts(item) > 0) conflicts.add(item);
		}
		return conflicts.size();
	}
	
	public boolean stateCorrect() {
		State s = genState();
		for (Constraint c : constraints) {
			if (!c.satisfies(s)) return false;
		}
		return true;
	}
	/**
	 * Finds the number of conflicts for one variable
	 * @param item
	 * @return
	 */
	public int numConflicts(Item item) {
		int i = 0;
		for (Constraint constraint : constraints) {
			if (!constraint.satisfies(item, assignments)) {
				i++;
			}
		}
		return i;
	}
	
	/**
	 * Search up to max times. Does not handle local minima well, might want to try several times.
	 * @param max
	 * @return
	 */
	public State search(int max) {
		int i = 0;
		
		while (i < max) {
			i++;
			genState();
			findConflicts();
			
			if (conflicts.size() == 0) {
				if (stateCorrect()) {
					System.out.println("MinConflicts found after " + i);
					return genState();
				} else {
					System.out.println("MinConflicts has no variable conflicts, but the state isn't correct. Probably no solution.");
					return null;
				}
			}
			
			Item item = this.conflicts.get(r.nextInt(this.conflicts.size())); // Grab random item
			
			Bag bestBag = this.assignments.get(item);
			
			int minConflicts = numConflicts(item);
			
			for (Bag bag : values) {
				assignItem(bag, item);
				int con = numConflicts(item);
				if ((con < minConflicts) || ((con == minConflicts) && (r.nextDouble() > 0.5d))) { // randomly break ties
					minConflicts = con;
					bestBag = bag;
				}
			}
			
			assignItem(bestBag, item);
		}
		
		return null;
	}
	
	
}
