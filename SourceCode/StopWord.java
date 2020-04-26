import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;

import org.tartarus.snowball.ext.PorterStemmer;

public class StopWords {
	//Initialize any variables needed throughout the class
	static int numOfFiles = 10;
	static ArrayList<String> tokenList = new ArrayList<String>();
	static ArrayList<String> wordList = new ArrayList<String>();
	static ArrayList<String> tempArray = new ArrayList<String>();

	//String stopwordRemoval method is to remove all stopwords from the token files 
	public static String stopwordRemoval(String stopWordPath, String tokenFolderPath, String stopWordRemovalFile) throws IOException {
		
		//Use scanner to read the input file
		String stopwordsFile = stopWordPath;
		Scanner readFile = null; 
		try {
			readFile = new Scanner(new File(stopwordsFile));
		}
		catch (FileNotFoundException e) {
			System.out.println("Error");
		}
		
		System.out.println("\n" + "Number of Tokens After Stopword Removal");
		
		//Add all the stopwords into an arraylist
		String readStopWords;
		while(readFile.hasNextLine()) {
            readStopWords = readFile.nextLine();
            wordList.add(readStopWords);
        }
		
		//Use a for loop to go through each data file 
		for (int i = 1; i <= numOfFiles; i++) {
			//Use FileInputStream to get the file path and use BufferedReader and InputStreamReader to read the files
			FileInputStream files = new FileInputStream(tokenFolderPath + "/a" + i + ".txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(files));
			
			//Use FileWriter to indicate the path to where the data will be outputed and BufferedWriter to write to the indicated files
			FileWriter fileStream = new FileWriter(stopWordRemovalFile + "/a" + i + ".txt"); 
	        BufferedWriter outputStream = new BufferedWriter(fileStream);
			
	        //Use temporary arraylist to get the number of tokens for each file after every iteration. 
			String readTokens;
			while((readTokens = reader.readLine()) != null) {
				tokenList.add(readTokens);
				tempArray.add(readTokens);
			}
			tempArray.removeAll(wordList);
			for (int j = 0; j < tempArray.size(); j++) {
				//Sort the arraylist in alphabetical order
				Collections.sort(tempArray);
				outputStream.write(tempArray.get(j).toString() + "\n");
			}
			System.out.println("Tokens in File " + i + ":  " + tempArray.size());
			tempArray.clear();	
			
			outputStream.close();
		}
		
		//After temporary arraylist remove all stopwords from the one non temporary arraylist
		tokenList.removeAll(wordList);
		Collections.sort(tokenList);
		System.out.println("Total Number of Tokens After Stopword Removal:  " + tokenList.size());
		System.out.println("See the Stopword_Removal folder to view the results");
		return stopWordPath;
	}
	
}