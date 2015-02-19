package AIProject4;

import java.io.PrintStream;

public class LogPrinting {

	PrintStream out;
	
	public LogPrinting(PrintStream out) {
		this.out = out;
	}
	
	public String printOptions(Boolean H, Boolean FC){
		StringBuilder sb = new StringBuilder();
		sb.append("Heuristics: ");
		sb.append(H);
		sb.append(", Forward Checking: ");
		sb.append(FC);
		out.println(sb.toString());
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
		out.println(sb.toString());
		return sb.toString();
	}
	
	public String printRemoved(Assignment assignment){
		StringBuilder sb = new StringBuilder();
		sb.append("Removed <variable, value>: ");
		sb.append(assignment.item.letter);
		sb.append(", ");
		sb.append(assignment.bag.letter);
		out.println(sb.toString());
		return sb.toString();
	}
	
	public String printTry(Item item){
		StringBuilder sb = new StringBuilder();
		sb.append("Trying variable: ");
		sb.append(item.letter);
		out.println(sb.toString());
		return sb.toString();
	}
	
	public String printTry(Bag bag){
		StringBuilder sb = new StringBuilder();
		sb.append("Trying value: ");
		sb.append(bag.letter);
		out.println(sb.toString());
		return sb.toString();
	}
	
}
