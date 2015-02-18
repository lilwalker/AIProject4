package AIProject4;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


public class CSPApp {

	public static void main(String[] args) throws IOException {
		
		Reader reader = new Reader("samples/input3.txt");
		Constraints constraints = new Constraints(); 
		constraints = reader.importData();
		CSP csp = new CSP(constraints);
		csp.solve();
		
		System.out.print(PrintingUtils.genBagOutput(csp.bags));

	}

}
