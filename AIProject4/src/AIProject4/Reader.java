package AIProject4;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Reader {
	
	Path path;
	
	public Reader(String name){
		this.path = Paths.get(name);
	}
	
	/*
	 * Parse the file into a Constraints object
	 */
	public Constraints importData() throws IOException{
		// Init Constraints object
		Constraints constraints = new Constraints();
		// Read in the Reader object's file into a list of Strings
		List<String> lines = Files.readAllLines(path);
		int linenum = 1;
		int vars = 0; //the number of variables
		int bags = 0; //the number of bags
		ArrayList<String> inclusive = new ArrayList<String>();
		ArrayList<String> exclusive = new ArrayList<String>();
		// lines[0] is just the first header for items so skip it
		// First set of data: items
		// Until we get to the next header...
		while (!lines.get(linenum).startsWith("#")){
			// The line is an item so add the item to the constraints
			constraints.addItems(lines.get(linenum));
			// increase line number
			linenum++;
		}
		// Skip the next header for bags
		linenum++; 
		//reached next set of data: bags
		// Until next header is reached...
		while (!lines.get(linenum).startsWith("#")){
			constraints.addBags(lines.get(linenum));
			linenum++;
		}
		linenum++;
		//next set of data: fit limits
		while (!lines.get(linenum).startsWith("#")){ 
			constraints.addLimits(lines.get(linenum));
		}
		linenum++;
		//next set of data: unary inclusive
		while (!lines.get(linenum).startsWith("#")){
			inclusive.add(lines.get(linenum));
			linenum++;
		}
		//next set of data: unary exclusive
		linenum++;
		while (!lines.get(linenum).startsWith("#")){
			exclusive.add(lines.get(linenum));
			linenum++;
		}
		constraints.addCMatrix(inclusive,exclusive);
		return constraints;
	}
	

}
