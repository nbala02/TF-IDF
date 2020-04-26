/*
 * October 16, 2019
 */

/**
 *
 * @author Neha Bala
 *
 */

import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main (String [] args) throws IOException {
		//Use scanner to get user input
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Enter the file path to the Data folder: ");
		String dataFolderPath = keyboard.nextLine();
		System.out.println("\n" + "Enter the file path to the Tokens folder: ");
		String tokenFolderPath = keyboard.nextLine();
		
		//Call the tokenization method to tokenize all the files in the Data folder
		try {
			Tokens.tokenization(dataFolderPath, tokenFolderPath);
			Tokens.countTokens(tokenFolderPath);
		} 
		catch (Exception e) {
			System.out.println("Error");
		}
							
		System.out.println("\n" + "Enter the file path to the stopWords.txt file: ");
		String stopWordPath = keyboard.nextLine();
		System.out.println("\n" + "Enter the file path to the Stopword_Removal folder: ");
		String stopWordRemovalFile = keyboard.nextLine();
		
		//Call the stopWordRemoval method to remove all the stop words
		try {
			StopWords.stopwordRemoval(stopWordPath, tokenFolderPath, stopWordRemovalFile);
		}
		catch (Exception e) {
			System.out.println("Error");
		}

		
		System.out.println("\n" + "Enter the file path to the Stemming folder: ");
		String stemmingFilePath = keyboard.nextLine();
		
		//Call the stemming method to bring all the terms to its base word
		try {
			Stemming stem = new Stemming();
			stem.stemming(stopWordRemovalFile, stemmingFilePath);
		}
		catch (Exception e) {
			System.out.println("Error");
		}
		
		
		System.out.println("\n" + "Enter the file path to the TermFrequencies folder: ");
		String termFrequenciesPath = keyboard.nextLine();
		
		//Call the termFrequency method to calculate how many times each word appears in each document
		try {
			TermFrequency termFrequency = new TermFrequency();
			termFrequency.termFrequency(stemmingFilePath, termFrequenciesPath);
		}
		catch (Exception e) {
			System.out.println("Error");
		}
		
		
		System.out.println("\n" + "Enter the file path to the TFIDF/TF folder: ");
		String TFpath = keyboard.nextLine();
		//Call the calculateTF method 
		try {
			TFIDF tf = new TFIDF();
			tf.calculateTF(termFrequenciesPath, TFpath);
		}
		catch (Exception e) {
			System.out.println("Error");
		}
		
		System.out.println("\n" + "Enter the file path to the stemmed.txt file: ");
		String stemFile= keyboard.nextLine();
		System.out.println("\n" + "Enter the file path to the TFIDF/IDF/trackDoc.txt file: ");
		String trackDoc= keyboard.nextLine();
		//Call the trackDoc method
		try {
			TFIDF doc = new TFIDF();
			doc.trackDoc(stemFile, trackDoc, stemmingFilePath);
		}
		catch (Exception e) {
			System.out.println("Error");
		}
		
		//Call the finalTrackDoc method
		System.out.println("\n" + "Enter the file path to the TFIDF/IDF/finalTrackDoc.txt file: ");
		String finalTrackDoc= keyboard.nextLine();
		try {
			TFIDF removeDuplicate = new TFIDF();
			removeDuplicate.removeDuplicates(trackDoc, finalTrackDoc);
		}
		catch (Exception e) {
			System.out.println("Error");
		}
		
		//Call the calculate the TFIDF method
		TFIDF tfidf = new TFIDF();
		tfidf.calculateTFIDF(finalTrackDoc, TFpath);
	}
}