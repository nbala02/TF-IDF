import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Tokens {
	//Initialize any variables needed throughout the class
	static int numOfFiles = 10;
	
	//String tokenization method is created to tokenize the 10 Data files
	public static String tokenization(String dataFolderPath, String tokenFolderPath) throws IOException {
		
		//Use the for loop to go through each data file
		for (int i = 1; i <= numOfFiles; i++) {
			//Use FileInputStream to get the file path and use BufferedReader and InputStreamReader to read the files
			FileInputStream files = new FileInputStream(dataFolderPath + "/a" + i + ".txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(files));
			
			//Use FileWriter to indicate the path to where the data will be outputed and BufferedWriter to write to the indicated files
			FileWriter fileStream = new FileWriter(tokenFolderPath + "/a" + i + ".txt"); 
	        BufferedWriter outputStream = new BufferedWriter(fileStream);
			
			String readDocuments;
			
			//Use while loop to read each line
			//While reading each line we remove special characters, convert the words to lowercase and tokenize the file
			while((readDocuments = reader.readLine()) != null) {
				readDocuments = readDocuments.replaceAll("[^a-zA-Z0-9\\s+]", "");
				String toLowerCase = readDocuments.toLowerCase();
				
	            StringTokenizer string = new StringTokenizer(toLowerCase);
	            
	          //Output all the tokens to a token file
	            while (string.hasMoreTokens()) {
	            	outputStream.write(string.nextToken() + "\n");
	            }
	        }
			//Close BufferedWriter
			outputStream.close();
		}
		return dataFolderPath;
	}
	
	
	//String countTokens method is created to count all the tokens in each file before stopword removal
	public static String countTokens(String tokenFolderPath) throws IOException {
		
		System.out.println("\n" + "Number of Tokens Before Stopword Removal");
		
		//Use the for loop to go through each data file
		for (int i = 1; i <= numOfFiles; i++) {
			String file = tokenFolderPath + "/a" + i + ".txt";
			
			//Use scanner to read the token folder
			Scanner read = null; 
			try {
				read = new Scanner(new File(file));
			}
			catch (FileNotFoundException e) {
				System.out.println("Error");
			}
			
			//Use the while loop to count the number of lines that are in the file
			int count = 0;
			while (read.hasNextLine()) {
				  count++;
				  read.nextLine();
			}
			
			//Output the number of tokens in each file before removing all stopwords
			System.out.println("Tokens in File " + i + ":  " + count);
		}		
		System.out.println("See the Tokens folder to view the results");
		return tokenFolderPath;
	}
}