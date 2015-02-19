package AIProject4;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


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
		PrintingUtils.printBags(mcs.search(10000).getBags());

	}

}
