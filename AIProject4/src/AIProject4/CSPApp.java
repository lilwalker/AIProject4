package AIProject4;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CSPApp {

	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			System.out.println("Usage: java -jar name_of_jar.jar <input file> [num]");
			System.out.println("num: 0 = Just Backtracking");
			System.out.println("     1 = BT + Heuristic");
			System.out.println("     2 = BT + H + FC");
			System.exit(1);
		}
		
		if (!new File(args[0]).isFile()) {
			System.out.println(args[0] + " is not a file!");
			System.exit(1);
		}
		
		boolean useHeuristic, useFC;
		
		int num = 2;
		if (args.length == 2) {
			num = Integer.parseInt(args[1]);
		}
		

		switch (num) {
			case 0:
				useHeuristic = false;
				useFC = false;
				break;
			case 1:
				useHeuristic = true;
				useFC = false;
				break;
			case 2:
			default:
				useHeuristic = true;
				useFC = true;
				break;
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
		CSP csp = new CSP(constraints, useHeuristic, useFC, log);
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
