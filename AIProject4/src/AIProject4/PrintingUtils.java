package AIProject4;

import java.util.Collection;

public class PrintingUtils {
	public static String genBagOutput(Collection<Bag> bags) {
		if (bags.size() == 0) {
			return "No solution Found\n\n";
		}
		
		StringBuilder sb = new StringBuilder();
		
		for (Bag bag : bags) {
			sb.append(bag.letter);
			int weight = 0;
			for (Item item : bag.contents) {
				sb.append(' ').append(item.letter);
				weight += item.weight;
			}
			sb.append('\n');
			sb.append("number of items: ").append(bag.contents.size()).append('\n');
			sb.append("total weight: ").append(weight).append('\n');
			sb.append("wasted capacity: ").append(bag.capacity - weight).append("\n\n");
		}
		
		return sb.toString();
	}
}
