package AIProject4;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


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
			linenum++;
		}
		linenum++;
		//next set of data: unary inclusive
		while (!lines.get(linenum).startsWith("#")){
			constraints.addUnaryInc(lines.get(linenum));
			linenum++;
		}
		linenum++;
		//next set of data: unary exclusive
		while (!lines.get(linenum).startsWith("#")){
			constraints.addUnaryEx(lines.get(linenum));
			linenum++;
		}
		linenum++;
		//next set of data: binary eq
		while (!lines.get(linenum).startsWith("#")){
			constraints.addBinaryEq(lines.get(linenum));
			linenum++;
		}
		linenum++;
		//next set of data: binary noteq
		while (!lines.get(linenum).startsWith("#")){
			constraints.addBinaryNotEq(lines.get(linenum));
			linenum++;
		}
		linenum++;
		//next set of data: binary mutex
		while (linenum < lines.size() && !lines.get(linenum).startsWith("#")){
			constraints.addMutex(lines.get(linenum));
			linenum++;
		}
		linenum++;
		
		return constraints;
	}
	

}
