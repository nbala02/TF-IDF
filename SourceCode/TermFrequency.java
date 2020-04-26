import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class TermFrequency {
	//Initialize any variables needed throughout the class
	int numOfFiles = 10;
	ArrayList<String> filesList = new ArrayList<String>();
	ArrayList<String> wordList = new ArrayList<String>();
	TreeMap<String, Integer> termFrequency = new TreeMap<String, Integer>();
	String [] tokens;
	String highestTF = null;
	
	//String termFrequency is used to determine how many times does each word appears and what is the highest term frequency in each document
	public String termFrequency(String stemmingFilePath, String termFrequenciesPath) throws IOException{
		
		//Use a for loop to go through each data file
		for (int i = 1; i <= numOfFiles; i++) {
			//Use FileInputStream to get the file path and use BufferedReader and InputStreamReader to read the files
			FileInputStream files = new FileInputStream(stemmingFilePath + "/a" + i + ".txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(files));

			//Use FileWriter to indicate the path to where the data will be outputed and BufferedWriter to write to the indicated files
			FileWriter fileStream = new FileWriter(termFrequenciesPath + "/a" + i + ".txt"); 
			BufferedWriter outputStream = new BufferedWriter(fileStream);

			//Use a while loop to read through each line and divide the termfrequency documents into columns
			String line;
			while ((line = reader.readLine()) != null){   
			    tokens = line.split(",");
			    //Check to see if the termfrequency contains the term being read and accordingly organize the TreeMap
			    for (String docTerm : tokens){
			        if(termFrequency.containsKey(docTerm)){   
			        	termFrequency.put(docTerm, termFrequency.get(docTerm)+1);
			        }
			        else{
			        	termFrequency.put(docTerm, 1);
			        }
			    }
			    line = reader.readLine();
			}

			Set<Entry<String, Integer>> frequencySet = termFrequency.entrySet();
			ArrayList<Entry<String, Integer>> frequencyList = new ArrayList<Entry<String,Integer>>(frequencySet);

			frequencyList.stream().distinct().collect(Collectors.toList());
			
			//Get the keys and values of document and also will be determining the highest term frequency. 
			//All the term frequencies will be outputed into the files.
			int count = 0;
			for (Entry<String, Integer> docWords : frequencyList){
			    if (docWords.getValue() != null){
			        outputStream.write(docWords.getKey() + ": " + docWords.getValue() + "\n");
			    }
			    if (docWords.getValue() > count) {
			    	highestTF = docWords.getKey();
			    	count = docWords.getValue();
			    }
			}
			outputStream.write(highestTF + ": " + count);
			outputStream.close();
		}
		System.out.println("See TermFrequencies Folder for to view the results");
		return termFrequenciesPath;
	}

}