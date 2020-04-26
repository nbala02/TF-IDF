import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class TFIDF {
	//Initialize any variables needed throughout the class
	int numOfFiles = 10;
	static ArrayList<Double> tfidfTest = new ArrayList<Double>();
	static double [][] vectorCalc; 
	static Double [] doc1;
	static Double [] doc2;
	
	//String calculateTF method is to calculate the TF by taking the highest frequency of each file and dividing it with all other documents
	public String calculateTF(String termFrequenciesPath, String TFpath) throws IOException {
		String [] tempColumns = null;
		String [] fileColumns = null;

		//Use a for loop to go through each data file 
		for (int i = 1; i <= numOfFiles; i++) {
			//Use FileInputStream to get the file path and use BufferedReader and InputStreamReader to read the files
			FileInputStream tempFiles = new FileInputStream(termFrequenciesPath +"/a" + i + ".txt");
			BufferedReader tempReader = new BufferedReader(new InputStreamReader(tempFiles));
			
			FileInputStream files = new FileInputStream(termFrequenciesPath + "/a" + i + ".txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(files));
			
			//Use FileWriter to indicate the path to where the data will be outputed and BufferedWriter to write to the indicated files
			FileWriter fileStream = new FileWriter(TFpath + "/a" + i + ".txt"); 
	        BufferedWriter outputStream = new BufferedWriter(fileStream);
						
			//Read the tempFile and split it into columns
			String tempLine;
			while ((tempLine = tempReader.readLine()) != null){
				tempColumns = tempLine.split(" ");
			}
			
			//Obtain the highestFrequency in each document
			String highestTF = tempColumns[tempColumns.length - 1];
			
			//Parse the frequency column into integer in order to use it to compute the TF
			int numHighestTF = Integer.parseInt(highestTF);
			
			System.out.println("Highest Term Frequency in Document "+ i + ":   " + numHighestTF);
			
			//Read the term frequencies files and split each file into columns
			String line;
			while ((line = reader.readLine()) != null){
				fileColumns = line.split(" ");
				
				//Indicate which column is referring to which aspect of the data and put the columns into the variables
				String tokens = fileColumns[0];
				String frequency = fileColumns[1];
				
				//Parse the number of frequencies into integer to compute the TF
				int numFrequency = Integer.parseInt(frequency);
				
				//Convert the TF by diving the number of frequency by the highest term frequency
				//Parse the output into double to get a decimal output
				double getTF = (double) numFrequency / numHighestTF;
				outputStream.write(fileColumns[0] + " " + fileColumns[1] + ": " + getTF + "\n");
			}
			outputStream.close();
		}
		System.out.println("See the TF folder under TFIDF to view the results");
		return TFpath;
	}
	
	//String trackDoc method is used to track how many times the code runs 
	public String trackDoc(String stemFile, String trackDoc, String stemmingFilePath) throws IOException {
		//Use FileInputStream to get the file path and use BufferedReader and InputStreamReader to read the files
        FileInputStream stemmedFile = new FileInputStream(stemFile);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stemmedFile));
		
		//Use FileWriter to indicate the path to where the data will be outputed and BufferedWriter to write to the indicated files
		FileWriter fileStream = new FileWriter(trackDoc); 
        BufferedWriter outputStream = new BufferedWriter(fileStream);
		
		int count = 0;
		
		//Use the while loop to read through the stemming file and compare the terms to the terms in the documents
		String stemmedLine;
		while ((stemmedLine = reader.readLine()) != null) {
			for (int i = 1; i < numOfFiles; i++) {
				Scanner inputStream = null;
	        	String docFile = stemmingFilePath + "/a" + i + ".txt";
				try {
					inputStream = new Scanner(new File(docFile));
				}
				catch(FileNotFoundException e) {
					System.out.println("Error opening the file " + docFile);
					System.exit(0);
				}
				String fileLine;
				//Count the number of documents each word is present in
				while (inputStream.hasNextLine()) {
					fileLine = inputStream.nextLine();
					if (fileLine.equalsIgnoreCase(stemmedLine)) {
						count = i;
					}
				}
			}
			outputStream.write(stemmedLine + ": " + count + " Documents" + "\n");
		}
		outputStream.close();
		System.out.println("See the tracDoc file in TFIDF folder in the IDF folder for to view the results");
		return stemmedLine;
	}
	
	//String removeDuplicates is a method to remove all the duplicate data that we may have gotten
	public String removeDuplicates(String trackDoc, String finalTrackDoc) throws IOException {
		FileInputStream file = new FileInputStream(trackDoc);
		BufferedReader reader = new BufferedReader(new InputStreamReader(file));
		//Using a Hashset we are able to keep track of the unqiue words that are in the files removing all the duplicates
	    HashSet<String> uniqueWords = new HashSet<String>();
	    String line;
	    
	    while ((line = reader.readLine()) != null) {
	    	uniqueWords.add(line);
	    }
	    reader.close();
	    
		//Use FileWriter to indicate the path to where the data will be outputed and BufferedWriter to write to the indicated files
	    FileWriter fileStream = new FileWriter(finalTrackDoc); 
        BufferedWriter outputStream = new BufferedWriter(fileStream);
	    
	    for (String noDuplicates : uniqueWords) {
	    	outputStream.write(noDuplicates);
	    	outputStream.newLine();
	    }
	    outputStream.close();
	    System.out.println("See the finalTrackDoc file in TFIDF folder in the IDF folder for to view the results");
		return line;
	}
	
	//String calculate the TFIDF is used to calculate the TF and then the IDF and multiply the two to get the results
	public String calculateTFIDF(String finalTrackDoc, String TFpath) throws IOException {
		int N = 10;
		int numOfFiles = 10;
		String [] termArray = null;
		String [] lineArray = null;
		String finalTF = null;
		String finalIDF = null;
		String finalTFIDF = null;
		//ArrayList<String> tfidfTest = new ArrayList<String>();
		
		//Use FileInputStream to get the file path and use BufferedReader and InputStreamReader to read the files
		FileInputStream termFile = new FileInputStream(finalTrackDoc);
		BufferedReader reader = new BufferedReader(new InputStreamReader(termFile));
		
		String termLine;
		while ((termLine = reader.readLine()) != null) {
			termArray = termLine.split(" ");
			String term = termArray[0];
			//We obtain how many times the term appears and parse it into an array
			double termAppearances = Double.parseDouble(termArray[1]);
			//Using double we calcualte the TF using Math.log method
			double idf = Math.log(N / termAppearances);
			
			//Use the for loop to loop through each file
			for (int i = 1; i <= numOfFiles; i++) {
				//Use FileInputStream to get the file path and use BufferedReader and InputStreamReader to read the files
				FileInputStream tfFile = new FileInputStream(TFpath + "/a" + i + ".txt");
				BufferedReader tfReader = new BufferedReader(new InputStreamReader(tfFile));
						        
				String fileLine;
				while((fileLine = tfReader.readLine()) != null) {
					lineArray = fileLine.split(" ");
					
					String docTerm = lineArray[0];
					//If the document terms is equal to the term we are looking for then calculate the TFIDF
					if (docTerm.equalsIgnoreCase(term)) {
						double tf = Double.parseDouble(lineArray[2]);
						double tfidf = tf * idf;
						finalTF = Double.toString(tf);
						finalIDF = Double.toString(idf);
						finalTFIDF = Double.toString(tfidf);
						tfidfTest.add(tfidf);	
						System.out.println(finalTF + " * " + finalIDF + " = " + finalTFIDF);
					}
					
				}
			}
			
		}		
		return TFpath;
	}
}






