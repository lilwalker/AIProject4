package AIProject4;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CSPApp {

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Usage: java -jar name_of_jar.jar <input file>");
			System.exit(1);
		}
		
		if (!new File(args[0]).isFile()) {
			System.out.println(args[0] + " is not a file!");
			System.exit(1);
		}
		
		
		
		
		SimpleDateFormat dt = new SimpleDateFormat("yyyyMMddHHmmss");
		File logFile = new File("log_file_" + dt.format(new Date()));
		File outFile = new File("output_" + dt.format(new Date()));
		File logFileMinConflicts = new File("log_file_min_conflicts_" + dt.format(new Date()));
		
		PrintStream log = new PrintStream(logFile);
		PrintStream out = new PrintStream(outFile);
		PrintStream lmc = new PrintStream(logFileMinConflicts);
		
		Reader reader = new Reader(args[0]);
		Constraints constraints = new Constraints(); 
		constraints = reader.importData();
		CSP csp = new CSP(constraints, true, true, log);
		csp.solve();
		
		System.out.print(PrintingUtils.genBagOutput(csp.bags)); // Our normal output
		out.print(PrintingUtils.genBagOutput(csp.bags)); // Our normal output
		
		lmc.println("######### MIN CONFLICTS ##########");
		MinConflictsSearch mcs = new MinConflictsSearch(constraints.constraints, constraints.items, constraints.bags);
		State result = mcs.search(5000);
		
		if (result == null) {
			lmc.println("Min Conflicts reached try limit and gave up. Perhaps it hit a local minima? Try again?");
		} else {
			lmc.print(PrintingUtils.genBagOutput(result.getBags()));
		}
		
		out.close();
		log.close();
		lmc.close();
	}
}
