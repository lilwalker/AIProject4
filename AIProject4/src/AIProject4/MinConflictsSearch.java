package AIProject4;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import AIProject4.constraints.Constraint;


public class MinConflictsSearch {

	Collection<Constraint> constraints;
	List<Item> variables;
	List<Bag> values;
	Map<Item, Bag> assignments;
	Random r = new Random();
	List<Item> conflicts;
	
	public MinConflictsSearch(Collection<Constraint> constraints, Collection<Item> variables, Collection<Bag> values) {
		this.constraints = constraints;
		this.variables = new ArrayList<Item>(variables);
		this.values = new ArrayList<Bag>(values);
		this.assignments = new HashMap<Item,Bag>();
		
		
		for (Item item : this.variables) {
			Bag minBag = this.values.iterator().next();
			int minVal = minBag.capacity - minBag.weight();
			for (Bag bag : this.values) {
				int val = minBag.capacity - minBag.weight();
			}
		}
		
		Iterator<Bag> it = this.values.iterator();
		Bag defaultBag = it.next();
		for (Item item : this.variables) {
			if (defaultBag.weight() + item.weight > defaultBag.capacity && it.hasNext()) defaultBag = it.next();
			this.assignments.put(item,defaultBag);
		}
		genState();
	}
	
	private void assignItem(Bag b, Item i) {
		this.assignments.put(i,b);
		genState();
	}
	
	private State genState() {
		for (Bag bag : this.values) {
			bag.contents = new ArrayList<Item>();
		}
		
		for (Item item : variables) {
			this.assignments.get(item).contents.add(item);
		}
		
		return new State(this.values, this.variables);
	}
	
	public int findConflicts() {
		this.conflicts = new ArrayList<Item>();
		for (Item item : variables) {
			if (numConflicts(item) > 0) conflicts.add(item);
		}
		return conflicts.size();
	}
	
	public int numConflicts(Item item) {
		int i = 0;
		for (Constraint constraint : constraints) {
			if (!constraint.satisfies(item, assignments)) {
				i++;
			}
		}
		return i;
	}
	
	public State search(int max) {
		int i = 0;
		
		while (i < max) {
			i++;
			genState();
			findConflicts();
			
			if (conflicts.size() == 0) {
				return genState();
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
