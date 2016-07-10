import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MasterMindUser {
	final static int WIN = -1;
	final static int INVALIDWORD = -2;
	public static String[] words = new String[267751];
	public static int wordsize=5;
	public static String computerword;

	private static String removeDuplicateCharacters(String word) {
		char[] chars = word.toCharArray();
		Arrays.sort(chars);
		//System.out.println(chars);
		word = String.valueOf(chars);
		String ourString="";
	    	int j = 0;
	    	for (int i=0; i<word.length()-1 ; i++){
	        	j = i+1;
	        	if(word.charAt(i)!=word.charAt(j)){
	            		ourString+=word.charAt(i);
	        	}
	    	}
		return ourString;
	}

	public static String getRandomWord() {
		
		int randomindex = new Random().nextInt(words.length);
		while (hasDuplicatedCharacters(words[randomindex]) ) {
				randomindex = new Random().nextInt(words.length);
		}
		return words[randomindex];
	}

	public static boolean hasDuplicatedCharacters (String word) {
		for (char c : word.toCharArray()) {
			if (word.indexOf(c) != word.lastIndexOf(c)) {
				return true;
			}
		}
		return false;
	}

	public static int getCommonLetters(String word) {
		if(!(isValidWord(word)))
			return INVALIDWORD;
		
		if (word.equals(computerword)) 
			return WIN;
		
		word = removeDuplicateCharacters(word);
		int common = 0;
		for (char c : word.toCharArray()) {
			if (computerword.indexOf(c) != -1) {
				common++;
			}
		}
		return common;
	}
	
	public static boolean isValidWord(String word){
		for(int i=0; i<words.length; i++){
			if((word.equals(words[i]))){
				//System.out.println(words[i]+" "+word);
				return true;
			}
		}return false;
	}

	public static void main(String[] args) throws FileNotFoundException {
		int numOfCommonLetters ;
		wordsize = 5;
		try{
			File file = new File("SowPods.txt") ;
			Scanner s = new Scanner(file);
		    	String line;
		    	int k=0;
		    	while(s.hasNextLine()){
		    		line = s.nextLine();
		    		words[k] = line;
		    		//System.out.print(words[i] +"   ");
		    		k++;
		    	}
		    	s.close();
		 }
		 catch (Exception e){
		    	System.err.format("Exception occurred trying to read ");
		    	e.printStackTrace();
		 }
		 computerword = getRandomWord();
		 System.out.println("Computer has a word\nEnter your guess");
		 System.out.println("computer word : "+"("+computerword+")");
		 Scanner scan = new Scanner(System.in);
		 String word = scan.next().toLowerCase();
	   	 numOfCommonLetters = getCommonLetters(word);
		 while ( numOfCommonLetters > WIN || numOfCommonLetters == INVALIDWORD ) {
			if (numOfCommonLetters > WIN)
				System.out.println( numOfCommonLetters + " letters in common");
			if (numOfCommonLetters	== INVALIDWORD)
				System.out.println("INVALID WORD");
			numOfCommonLetters = getCommonLetters(scan.next().toLowerCase());
		 }
		 if(numOfCommonLetters==WIN)
			System.out.println("you won");
	}
}
