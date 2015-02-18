package AIProject4;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


public class CSPApp {

	public static void main(String[] args) throws IOException {
		
		
		Reader reader = new Reader("samples/input1.txt");
		Constraints constraints = new Constraints(); 
		constraints = reader.importData();
		CSP csp = new CSP(constraints);
		Boolean print = csp.arcConsistency();
		System.out.println(print);

	}

}
