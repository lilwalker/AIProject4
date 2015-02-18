package AIProject4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class CSP {
	
	ArrayList<Bag> bags;
	ArrayList<Item> items;
	Constraints constraints;
	ArrayList<Arc> arcs;
	HashMap<String, ArrayList<Bag>> domains;
	
	CSP(ArrayList<Item> items, ArrayList<Bag> bags, Constraints constraints){
		this.items = items;
		this.bags = bags;
		this.constraints = constraints;
		this.arcs = new ArrayList<Arc>();
		this.domains = makeDomains();
	}
	
	CSP(Constraints constraints){
		this.items = constraints.items;
		this.bags = constraints.bags;
		this.constraints = constraints;
		this.arcs = new ArrayList<Arc>();
		this.domains = makeDomains();
	}
	
	public void solve(){
		arcConsistency();
		System.out.println(domains);
		Item next = pickMRV();
		System.out.println(next.letter);
		ArrayList<Bag> nextbag = LCVList(next);
		System.out.println(nextbag);
	}

	/*public void backtrackingSearch(){
		ArrayList<Item> assigned = new ArrayList<Item>();
		assigned = items;
		
	}
	
	public backtrack(ArrayList<Assignment> assignments, ArrayList<Item> unassigned){
		if (assignments.size()==items.size()){
			return assignments;
		}
		var = pickMRV();
	}*/
	
	public ArrayList<Bag> LCVList(Item item){
		//PriorityQueue<Bag> lcv = new PriorityQueue<Bag>();
		//Comparator domainsize = new DomainSizeComp();
		ArrayList<Bag> lcv = new ArrayList<Bag>();
		ArrayList<Bag> unassigned = (ArrayList<Bag>) domains.get(item.letter).clone();
		int currentdomains = totalDomains();
		while(!unassigned.isEmpty()){
			Bag mostFlexible = unassigned.get(0);
			int bestelimdomains = currentdomains;
			for (Bag bag: unassigned){
				bag.addItem(item);
				if (arcConsistency()){
					int elimdomains = currentdomains - totalDomains();
					if (elimdomains < bestelimdomains){
						mostFlexible = bag;
						bestelimdomains = elimdomains;
					}
					
				}
				bag.removeItem(item);
				arcConsistency();
			}
			lcv.add(mostFlexible);
			unassigned.remove(mostFlexible);
		}
		return lcv;
	}
	
	public Bag pickLCV(Item item){
		Bag mostFlexible = domains.get(item.letter).get(0);
		int currentdomains = totalDomains();
		int bestelimdomains = currentdomains;
		for (Bag bag : domains.get(item.letter)){
			bag.addItem(item);
			if (arcConsistency()){
				int elimdomains = currentdomains - totalDomains();
				System.out.println(elimdomains);
				if (elimdomains < bestelimdomains){
					mostFlexible = bag;
					bestelimdomains = elimdomains;
				}
			}
			bag.removeItem(item);
			arcConsistency();
		}
		return mostFlexible;
	}
	
	private int totalDomains() {
		int total = 0;
		for (String key : domains.keySet()){
			total += domains.get(key).size();
		}
		return total;
	}

	public Item pickMRV(){
		
		int minremaining = domains.get(items.get(0).letter).size();
		Item currentpick = items.get(0);
		
		for (Item item : items){
			if (domains.get(item.letter).size() < minremaining){
				minremaining = domains.get(item.letter).size();
				currentpick = item;
			}
		}
		
		return currentpick;
		
	}
	
	
	public Boolean arcConsistency(){
		for (int i = 0; i < items.size()-1; i++){
			for (int j = i+1; j<items.size(); j++){
				arcs.add(new Arc(items.get(i), items.get(j)));
			}
		}
		
		while (!arcs.isEmpty()){
			Arc currentarc = arcs.remove(0);
			if (revise(currentarc)){
				if (domains.get(currentarc.item1.letter).isEmpty()){
					return false;
				}
				arcs.addAll(constraints.getNeighbors(currentarc.item1));
			}
		}
		System.out.println(domains);
		return true;
	}

	private Boolean revise(Arc currentarc) {
		Boolean revised = false;
		for (int i = 0; i<bags.size(); i++){
			bags.get(i).addItem(currentarc.item1);
			State statea = new State(bags);
			if (constraints.satisfiesAll(statea)){
				Boolean remove = true;
				for (Bag bag: bags){
					bag.addItem(currentarc.item2);
					State stateb = new State(bags);
					if (constraints.satisfiesAll(stateb)){
						remove = false;
					}
					bag.removeItem(currentarc.item2);
				}
				if (remove){
					for (int j = 0; j < domains.get(currentarc.item1.letter).size(); j++){
						if (domains.get(currentarc.item1.letter).get(j).letter.equals(bags.get(i).letter))
							domains.get(currentarc.item1.letter).remove(j);
					}
					revised = true;
				}
			}
			if (!constraints.satisfiesAll(statea)){
				for (int j = 0; j < domains.get(currentarc.item1.letter).size(); j++){
					if (domains.get(currentarc.item1.letter).get(j).letter.equals(bags.get(i).letter))
						domains.get(currentarc.item1.letter).remove(j);
				}
			}
			bags.get(i).removeItem(currentarc.item1);
		}
		return revised;
	}
	
	private HashMap makeDomains() {
		HashMap domains = new HashMap<String, ArrayList<Bag>>();
		for (Item item: items){
			domains.put(item.letter, bags.clone());
		}
		return domains;
	}

}
