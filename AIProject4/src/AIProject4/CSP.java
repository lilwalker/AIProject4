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

	/* 
	 * backTrackingSearch initiates the backtracking algorithm
	 */
	public ArrayList<Assignment> backtrackingSearch(){
		ArrayList<Assignment> assignments = backtrack(new ArrayList<Assignment>());
		return assignments;
		
	}
	
	/* 
	 * backtracking search algorithm. assignments of values to variables (bags to items) 
	 * and checking to make sure the assignment is within the specified constraints. 
	 * If the assignment is valid, the next variable is assigned a value and so on. 
	 * If an assignment is not valid, backtracking occurs and the program backs up the 
	 * search tree to the last satisfied assignment and makes another assignment.
	 */
	public ArrayList<Assignment> backtrack(ArrayList<Assignment> assignments){
		if (unassigned.isEmpty()){//every assignment is made
			return assignments;//done. return the assignments
		}
		Item var = pickMRV(assignments); //Pick which variable to assign first
		log.printTry(var); //add to log file
		for (Bag bag : bags){//try adding the variable to each bag
			bag.addItem(var);
			log.printTry(bag);
			removeAssignedItem(var);//remove from list of unassigned variables
			State statea = new State(bags, items);//make a state with the new bag set
			if (forwardcheckingon){//only do if forward chekcing is enabled
				if(arcConsistency()){//check arc consistency for forward checking
					if (constraints.satisfiesAll(statea)){//if all constraints are satisfied
						Assignment assign = new Assignment(var, bag);
						assignments.add(assign);//add assignment of value to variable to list
						log.printAdd(assign);
						ArrayList<Assignment> result = backtrack(assignments);//call backtracking on altered CSP
						if (!result.isEmpty())//if there is a result, return
							return result;
					}
				}
			}
			else{
				trys++;
				if (constraints.satisfiesAll(statea)){//if all constraints are satisfied
					Assignment assign = new Assignment(var, bag);
					assignments.add(assign);
					log.printAdd(assign);
					ArrayList<Assignment> result = backtrack(assignments);//call backtracking on altered CSP
					if (!result.isEmpty())//if there is a result, return
						return result;
				}
			}
			bag.removeItem(var);//if not successful assignment, remove item from bag
			unassigned.add(var);//add variable back to unassigned
			log.printRemoved(new Assignment(var, bag));
			assignments = removeAssignment(assignments, new Assignment(var, bag));//remove assignment from list
		}
		return new ArrayList<Assignment>();
	}
	
	/* 
	 * helper to remove item from the assigned list
	 */
	private ArrayList<Assignment> removeAssignment(ArrayList<Assignment> assignments, Assignment assignment) {
		for (int a = 0; a < assignments.size(); a++){
			if (assignments.get(a).item.letter.equals(assignment.item.letter)){
				assignments.remove(a);
				return assignments;
			}
		}
		return assignments;
	}

	/*
	 * Order the list of values for the variable item according to the change in domain 
	 * caused by the assignment. Choose least changes to domain
	 */
	public ArrayList<Bag> LCVList(Item item){
		ArrayList<Bag> lcv = new ArrayList<Bag>();
		@SuppressWarnings("unchecked")
		ArrayList<Bag> unassigned = (ArrayList<Bag>) bags.clone();
		if (!heuristicson)
			return this.bags;
		int currentdomains = totalDomains();
		while(!unassigned.isEmpty()){//check all unassigned for new domain
			Bag mostFlexible = unassigned.get(0);
			int bestelimdomains = currentdomains;
			for (Bag bag: unassigned){
				bag.addItem(item);
				State statea = new State(bags, items);
				if (constraints.satisfiesAll(statea)){//check if constraints satisfied
					int elimdomains = currentdomains - totalDomains();
					if (elimdomains < bestelimdomains){
						mostFlexible = bag;
						bestelimdomains = elimdomains;
					}
					
				}
				bag.removeItem(item);
				this.unassigned.add(item);
				arcConsistency();//call arc consistency to update domains
			}
			lcv.add(mostFlexible);
			unassigned.remove(mostFlexible);
		}
		return lcv;
	}
	
	/* 
	 * pick variable to assign by the fewest number of possible values in its domain
	 */
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
	
	/*
	 * degree heuristic choses the item with the fewest associated constraints
	 */
	private Item degreeHeuristic(Item item1, Item item2) {
		int item1score = constraints.constrainsBTW(item1, unassigned);
		int item2score = constraints.constrainsBTW(item2, unassigned);
		if (item1score > item2score)
			return item1;
		return item2;
	}

	//assembles list of arcs. checks all arcs to ensure domains are valid
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
					//if the domain is empty, the assignment is not valid, return false
					return false;
				}
				arcs.addAll(constraints.getNeighbors(currentarc.item1));
			}
		}
		return true;
	}
	//changes the domain for the given arc
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
