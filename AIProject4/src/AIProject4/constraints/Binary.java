package AIProject4.constraints;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import AIProject4.Assignment;
import AIProject4.Bag;
import AIProject4.Item;
import AIProject4.State;

public abstract class Binary implements Constraint {

	String item1;
	String item2;
	
	public Binary(String item1, String item2) {
		this.item1 = item1;
		this.item2 = item2;
	}
	
	public static class Equals extends Binary {
		public Equals(String item1, String item2) {
			super(item1, item2);
		}

		@Override
		public boolean satisfies(State state) {
			ArrayList<String> assigned = new ArrayList<String>();
			int satisfied = 0;
			for (Bag bag : state.getBags()) {
				boolean citem1 = false;
				boolean citem2 = false;
				for (Item item : bag.contents) {
					assigned.add(item.letter);
					if (item.letter.equals(item1)) citem1 = true;
					if (item.letter.equals(item2)) citem2 = true;
				}
				if (!(citem1 ^ citem2)) satisfied = 1;
			}
			if (!assigned.contains(item1) || !assigned.contains(item2)) return true;
			if (satisfied==1) return true;
			return false;
		}

		@Override
		public boolean satisfies(Item variable, Map<Item, Bag> assignments) {
			if (!variable.letter.equals(item1) && !variable.letter.equals(item2)) {
				return true;
			}
			
			Bag bag = assignments.get(variable);
			
			boolean citem1 = false;
			boolean citem2 = false;
			for (Item item : bag.contents) {
				if (item.letter.equals(item1)) citem1 = true;
				if (item.letter.equals(item2)) citem2 = true;
			}
			
			return citem1 && citem2;
		}
	}
	
	public static class NotEquals extends Binary {
		public NotEquals(String item1, String item2) {
			super(item1, item2);
		}

		@Override
		public boolean satisfies(State state) {
			ArrayList<String> assigned = new ArrayList<String>();
			int satisfied = 1;
			for (Bag bag : state.getBags()) {
				boolean citem1 = false;
				boolean citem2 = false;
				for (Item item : bag.contents) {
					assigned.add(item.letter);
					if (item.letter.equals(item1)) citem1 = true;
					if (item.letter.equals(item2)) citem2 = true;
				}
				if (citem1 && citem2) satisfied = 0;
			}
			if (!(assigned.contains(item1) && assigned.contains(item2))) return true;
			if (satisfied==1) return true;
			return false;
		}

		@Override
		public boolean satisfies(Item variable, Map<Item, Bag> assignments) {
			if (!variable.letter.equals(item1) && !variable.letter.equals(item2)) {
				return true;
			}
			
			Bag bag = assignments.get(variable);
			
			boolean citem1 = false;
			boolean citem2 = false;
			for (Item item : bag.contents) {
				if (item.letter.equals(item1)) citem1 = true;
				if (item.letter.equals(item2)) citem2 = true;
			}
			
			return !(citem1 && citem2);
		}
	}
	
	public ArrayList<String> returnItems(){
		ArrayList<String> returnlist = new ArrayList<String>();
		returnlist.add(item1);
		returnlist.add(item2);
		return returnlist;
	}
	
	public boolean isNeighbor(Item item){
		if (item.letter.equals(item1)){
			return true;
		}
		if (item.letter.equals(item2)){
			return true;
		}
		return false;
	}
	public String getItem1(){
		return item1;
	}
	public String getItem2(){
		return item2;
	}
}
