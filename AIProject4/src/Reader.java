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
	
	public void importData() throws IOException{
		List<String> lines = Files.readAllLines(path);
		int linenum = 1;
		int vars = 0; //the number of variables
		int bags = 0; //the number of bags
		while (!lines.get(linenum).startsWith("#")){
			vars++;
		}
			
		/*for (int linenum = 1; linenum < lines.size(); linenum++){
			String line = lines.get(linenum);
			if (line.startsWith("#"))
					System.out.println(line);
			if (line.contains("variable")){
				linenum++;
				while (!line.startsWith("#"))
			}
		}*/
	}

}
