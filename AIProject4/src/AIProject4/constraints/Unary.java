package AIProject4.constraints;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import AIProject4.Bag;
import AIProject4.Item;
import AIProject4.State;

public abstract class Unary implements Constraint {
	public abstract boolean itemAllowedInBag(String item, String bag);
	
	public boolean satisfies(State state) {
		for (Bag bag : state.getBags()) {
			for (Item item : bag.contents) {
				if (!this.itemAllowedInBag(item.letter, bag.letter)) {
					return false;
				}
			}
		}
		return true;
	}
	
	protected String item;
	public String getItem() {
		return item;
	}

	public Set<String> getBags() {
		return bags;
	}

	protected Set<String> bags;
	protected Unary(String item, List<String> bags) {
		this.item = item;
		this.bags = new HashSet<String>();
		this.bags.addAll(bags);
	}
	
	public static class Inclusive extends Unary {
		public Inclusive(String bag, List<String> items) {
			super(bag,items);
		}

		@Override
		public boolean itemAllowedInBag(String item, String bag) {
			if (!item.equals(this.item)) return true;
			if (this.bags.contains(bag)) return true;
			return false;
		}
	}
	
	public static class Exclusive extends Unary {
		public Exclusive(String bag, List<String> items) {
			super(bag,items);
		}

		@Override
		public boolean itemAllowedInBag(String item, String bag) {
			if (!item.equals(this.item)) return true;
			if (this.bags.contains(bag)) return false;
			return true;
		}
	}
}
