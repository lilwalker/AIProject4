package AIProject4;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

public class CSP {
	
	ArrayList<Bag> bags;
	ArrayList<Item> items;
	ArrayList<Item> unassigned;
	Constraints constraints;
	ArrayList<Arc> arcs;
	HashMap<String, ArrayList<Bag>> domains;
	HashMap<String, ArrayList<Bag>> arcdomains;
	LogPrinting log;
	Boolean heuristicson;
	Boolean forwardcheckingon;
	int trys;
	
	@SuppressWarnings("unchecked")
	CSP(ArrayList<Item> items, ArrayList<Bag> bags, Constraints constraints, PrintStream out){
		this.items = items;
		this.bags = bags;
		this.constraints = constraints;
		this.arcs = new ArrayList<Arc>();
		this.domains = makeDomains();
		this.unassigned = (ArrayList<Item>) items.clone();
		this.log = new LogPrinting(out);
	}
	
	@SuppressWarnings("unchecked")
	CSP(Constraints constraints, Boolean heuristics, Boolean FC, PrintStream out){
		this.items = constraints.items;
		this.bags = constraints.bags;
		this.constraints = constraints;
		this.arcs = new ArrayList<Arc>();
		this.domains = makeDomains();
		this.arcdomains = makeDomains();
		this.unassigned = (ArrayList<Item>) items.clone();
		this.log = new LogPrinting(out);
		this.heuristicson = heuristics;
		this.forwardcheckingon = FC;
		log.printOptions(heuristicson, forwardcheckingon);
	}
	
	public ArrayList<Bag> solve(){
		//arcConsistency();
		backtrackingSearch();
		//System.out.println("Conflict Checks: "+trys);
		return bags;
	}

	public ArrayList<Assignment> backtrackingSearch(){
		ArrayList<Assignment> assignments = backtrack(new ArrayList<Assignment>());
		return assignments;
		
	}
	
	public ArrayList<Assignment> backtrack(ArrayList<Assignment> assignments){
		if (unassigned.isEmpty()){//every assignment is made
			return assignments;
		}
		Item var = pickMRV(assignments);
		log.printTry(var);
		for (Bag bag : bags){
			bag.addItem(var);
			log.printTry(bag);
			removeAssignedItem(var);
			State statea = new State(bags, items);
			if (forwardcheckingon){
				if(arcConsistency()){
					trys++;
					if (constraints.satisfiesAll(statea)){
						Assignment assign = new Assignment(var, bag);
						assignments.add(assign);
						log.printAdd(assign);
						ArrayList<Assignment> result = backtrack(assignments);
						if (!result.isEmpty())
							return result;
					}
				}
			}
			else{
				trys++;
				if (constraints.satisfiesAll(statea)){
					Assignment assign = new Assignment(var, bag);
					assignments.add(assign);
					log.printAdd(assign);
					ArrayList<Assignment> result = backtrack(assignments);
					if (!result.isEmpty())
						return result;
				}
			}
			bag.removeItem(var);
			unassigned.add(var);
			log.printRemoved(new Assignment(var, bag));
			assignments = removeAssignment(assignments, new Assignment(var, bag));
		}
		return new ArrayList<Assignment>();
	}
	
	private ArrayList<Assignment> removeAssignment(ArrayList<Assignment> assignments, Assignment assignment) {
		for (int a = 0; a < assignments.size(); a++){
			if (assignments.get(a).item.letter.equals(assignment.item.letter)){
				assignments.remove(a);
				return assignments;
			}
		}
		return assignments;
	}

	public ArrayList<Bag> LCVList(Item item){
		ArrayList<Bag> lcv = new ArrayList<Bag>();
		@SuppressWarnings("unchecked")
		ArrayList<Bag> unassigned = (ArrayList<Bag>) bags.clone();
		if (!heuristicson)
			return this.bags;
		int currentdomains = totalDomains();
		while(!unassigned.isEmpty()){
			Bag mostFlexible = unassigned.get(0);
			int bestelimdomains = currentdomains;
			for (Bag bag: unassigned){
				bag.addItem(item);
				State statea = new State(bags, items);
				if (constraints.satisfiesAll(statea)){
					int elimdomains = currentdomains - totalDomains();
					if (elimdomains < bestelimdomains){
						mostFlexible = bag;
						bestelimdomains = elimdomains;
					}
					
				}
				bag.removeItem(item);
				this.unassigned.add(item);
				arcConsistency();
			}
			lcv.add(mostFlexible);
			unassigned.remove(mostFlexible);
		}
		return lcv;
	}
	
	public Item pickMRV(ArrayList<Assignment> assignments){
		
		int minremaining = domains.get(items.get(0).letter).size();
		Item currentpick = unassigned.get(0);
		
		
		if (heuristicson){
			/*if (forwardcheckingon)
				arcConsistency();*/
			for (Item item : unassigned){
				if (domains.get(item.letter).size() == minremaining){
					minremaining = domains.get(item.letter).size();
					currentpick = degreeHeuristic(currentpick, item);
				}
				if (domains.get(item.letter).size() < minremaining){
					minremaining = domains.get(item.letter).size();
					currentpick = item;
				}
			}
		}
		
		return currentpick;
		
	}
	
	
	private Item degreeHeuristic(Item item1, Item item2) {
		int item1score = constraints.constrainsBTW(item1, unassigned);
		int item2score = constraints.constrainsBTW(item2, unassigned);
		if (item1score > item2score)
			return item1;
		return item2;
	}

	public Boolean arcConsistency(){
		for (int i = 0; i < unassigned.size()-1; i++){
			for (int j = i+1; j<unassigned.size(); j++){
				arcs.add(new Arc(unassigned.get(i), unassigned.get(j)));
			}
		}
		
		while (!arcs.isEmpty()){
			Arc currentarc = arcs.remove(0);
			if (revise(currentarc)){
				if (arcdomains.get(currentarc.item1.letter).isEmpty()){
					return false;
				}
				arcs.addAll(constraints.getNeighbors(currentarc.item1));
			}
		}
		return true;
	}

	private Boolean revise(Arc currentarc) {
		Boolean revised = false;
		for (int i = 0; i<bags.size(); i++){
			bags.get(i).addItem(currentarc.item1);
			State statea = new State(bags, items);
			if (constraints.satisfiesAll(statea)){
				Boolean remove = true;
				for (Bag bag: bags){
					bag.addItem(currentarc.item2);
					State stateb = new State(bags, items);
					if (constraints.satisfiesAll(stateb)){
						remove = false;
					}
					bag.removeItem(currentarc.item2);
				}
				if (remove){
					for (int j = 0; j < arcdomains.get(currentarc.item1.letter).size(); j++){
						if (arcdomains.get(currentarc.item1.letter).get(j).letter.equals(bags.get(i).letter))
							arcdomains.get(currentarc.item1.letter).remove(j);
					}
					revised = true;
				}
			}
			if (!constraints.satisfiesAll(statea)){
				for (int j = 0; j < arcdomains.get(currentarc.item1.letter).size(); j++){
					if (arcdomains.get(currentarc.item1.letter).get(j).letter.equals(bags.get(i).letter))
						arcdomains.get(currentarc.item1.letter).remove(j);
				}
			}
			bags.get(i).removeItem(currentarc.item1);
		}
		return revised;
	}
	
	@SuppressWarnings("unchecked")
	private HashMap<String, ArrayList<Bag>> makeDomains() {
		HashMap<String, ArrayList<Bag>> domains = new HashMap<String, ArrayList<Bag>>();
		for (Item item: items){
			domains.put(item.letter, (ArrayList<Bag>) bags.clone());
		}
		return domains;
	}
	
	private ArrayList<Item> removeAssignedItem(Item item) {
		for (int u = 0; u < unassigned.size(); u++){
			if (unassigned.get(u).letter.equals(item.letter)){
				unassigned.remove(u);
				return unassigned;
			}
		}
		return unassigned;
	
	}
	
	private int totalDomains() {
		int total = 0;
		for (String key : domains.keySet()){
			total += domains.get(key).size();
		}
		return total;
	}

}
