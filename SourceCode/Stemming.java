import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

//Use external Java library to run the stemming class
import org.tartarus.snowball.ext.PorterStemmer;

public class Stemming {
	//Initialize any variables needed throughout the class
	ArrayList<String> stemmer = new ArrayList<String>();
	ArrayList<String> tempStemmer = new ArrayList<String>();
	PorterStemmer stemming = new PorterStemmer();
	int numOfFiles = 10;
	
	//String stemming method is to stem the words to bring them to their base or root form
	public String stemming(String stopWordRemovalFile, String stemmingFilePath) throws IOException{
		
		//Use a for loop to go through each data file
		for (int i = 1; i <= numOfFiles; i++) {
			//Use FileInputStream to get the file path and use BufferedReader and InputStreamReader to read the files
			FileInputStream files = new FileInputStream(stopWordRemovalFile + "/a" + i + ".txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(files));
			
			//Use FileWriter to indicate the path to where the data will be outputed and BufferedWriter to write to the indicated files
			FileWriter fileStream = new FileWriter(stemmingFilePath + "/a" + i + ".txt"); 
	        BufferedWriter outputStream = new BufferedWriter(fileStream);
			
	        //Add each line of the files into an temporary stemmer arraylist to output the words into their root form
			String readStopwordDoc;
			while((readStopwordDoc = reader.readLine()) != null) {
				tempStemmer.add(readStopwordDoc);
			}
			//Use the for loop to get the size of the temporary stemmer arraylist and stem the words
			for(int j = 0; j < tempStemmer.size(); j++) {
				stemming.setCurrent(tempStemmer.get(j).toString());
				stemming.stem();
				outputStream.write(stemming.getCurrent() + "\n");
			}
			tempStemmer.clear();
			
			outputStream.close();
		}
		System.out.println("See Stemming Folder for to view the results");
		return stemmingFilePath;
	}
}