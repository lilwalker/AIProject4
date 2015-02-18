package AIProject4.constraints;

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
	
	public class Equals extends Binary {
		public Equals(String item1, String item2) {
			super(item1, item2);
		}

		@Override
		public boolean satisfies(State state) {
			for (Bag bag : state.getBags()) {
				boolean citem1 = false;
				boolean citem2 = false;
				for (Item item : bag.contents) {
					if (item.letter.equals(item1)) citem1 = true;
					if (item.letter.equals(item2)) citem2 = true;
				}
				if (citem1 ^ citem2) return false;
			}
			return true;
		}
	}
	
	public class NotEquals extends Binary {
		public NotEquals(String item1, String item2) {
			super(item1, item2);
		}

		@Override
		public boolean satisfies(State state) {
			for (Bag bag : state.getBags()) {
				boolean citem1 = false;
				boolean citem2 = false;
				for (Item item : bag.contents) {
					if (item.letter.equals(item1)) citem1 = true;
					if (item.letter.equals(item2)) citem2 = true;
				}
				if (citem1 && citem2) return false;
			}
			return true;
		}
	}
}
