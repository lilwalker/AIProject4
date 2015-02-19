package AIProject4;

public class LogPrinting {

	public String printOptions(Boolean H, Boolean FC){
		StringBuilder sb = new StringBuilder();
		sb.append("Heuristics: ");
		sb.append(H);
		sb.append(", Forward Checking: ");
		sb.append(FC);
		System.out.println(sb.toString());
		return sb.toString();
	}
	
	public void printLogLine(){
		
	}
	
	public String printAdd(Assignment assignment){
		StringBuilder sb = new StringBuilder();
		sb.append("Added <variable, value>: ");
		sb.append(assignment.item.letter);
		sb.append(", ");
		sb.append(assignment.bag.letter);
		System.out.println(sb.toString());
		return sb.toString();
	}
	
	public String printRemoved(Assignment assignment){
		StringBuilder sb = new StringBuilder();
		sb.append("Removed <variable, value>: ");
		sb.append(assignment.item.letter);
		sb.append(", ");
		sb.append(assignment.bag.letter);
		System.out.println(sb.toString());
		return sb.toString();
	}
	
	public String printTry(Item item){
		StringBuilder sb = new StringBuilder();
		sb.append("Trying variable: ");
		sb.append(item.letter);
		System.out.println(sb.toString());
		return sb.toString();
	}
	
	public String printTry(Bag bag){
		StringBuilder sb = new StringBuilder();
		sb.append("Trying value: ");
		sb.append(bag.letter);
		System.out.println(sb.toString());
		return sb.toString();
	}
	
}
