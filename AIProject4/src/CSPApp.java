import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


public class CSPApp {

	public static void main(String[] args) throws IOException {
		
		Path path = Paths.get("/samples/input1.txt");
		//System.out.println(path);
		
		Reader reader = new Reader("C:/Users/Lillian/Documents/2014-15/CS4341/AI-Project4/AIProject4/AIProject4/samples/input1.txt");
		reader.importData();
		

	}

}
