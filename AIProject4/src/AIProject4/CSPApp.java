package AIProject4;
import java.io.IOException;


public class CSPApp {

	public static void main(String[] args) throws IOException {
		
		Reader reader = new Reader("samples/input5.txt");
		Constraints constraints = new Constraints(); 
		constraints = reader.importData();
		CSP csp = new CSP(constraints, true, true);
		csp.solve();
		
		System.out.print(PrintingUtils.genBagOutput(csp.bags));
		
		System.out.println("######### MIN CONFLICTS ##########");
		MinConflictsSearch mcs = new MinConflictsSearch(constraints.constraints, constraints.items, constraints.bags);
		State result = mcs.search(5000);
		
		if (result == null) {
			System.out.println("Min Conflicts reached try limit and gave up. Perhaps it hit a local minima? Try again?");
		} else {
			PrintingUtils.printBags(result.getBags());
		}
	}
}
