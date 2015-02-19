package AIProject4;
import java.io.IOException;


public class CSPApp {

	public static void main(String[] args) throws IOException {
		
		Reader reader = new Reader("samples/input6.txt");
		Constraints constraints = new Constraints(); 
		constraints = reader.importData();
		CSP csp = new CSP(constraints, false, false);
		csp.solve();
		
		System.out.print(PrintingUtils.genBagOutput(csp.bags));
		
		System.out.println("######### MIN CONFLICTS ##########");
		MinConflictsSearch mcs = new MinConflictsSearch(constraints.constraints, constraints.items, constraints.bags);
		State result = mcs.search(50000);
		
		if (result == null) {
			System.out.println("Min Conflicts reached try limit and gave up, try again?");
		} else {
			PrintingUtils.printBags(result.getBags());
		}
	}
}
