package AIProject4;

import java.util.ArrayList;
import java.util.HashMap;

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
