package AIProject4.constraints;

import AIProject4.Bag;
import AIProject4.Item;
import AIProject4.State;

public class MutEx implements Constraint {

	String item1, item2, bag1, bag2;
	public MutEx(String item1, String item2, String bag1, String bag2) {
		this.item1 = item1;
		this.item2 = item2;
		this.bag1 = bag1;
		this.bag2 = bag2;
	}
	
	@Override
	public boolean satisfies(State state) {
		boolean mutex1 = false;
		boolean mutex2 = false;
		for (Bag bag : state.getBags()) {
			if (bag.letter.equals(bag1)) {
				for (Item item : bag.contents) {
					if (item.letter.equals(item1)) {
						mutex1 = true;
						break;
					}
				}
			}
			if (bag.letter.equals(bag2)) {
				for (Item item: bag.contents) {
					if (item.letter.equals(item2)) {
						mutex2 = true;
						break;
					}
				}
			}
		}
		return !(mutex1 && mutex2);
	}

}
