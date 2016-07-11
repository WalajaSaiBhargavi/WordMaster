import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class MasterMindComputer {
	final static int WIN = -1;
	
	public static ArrayList<String> words = new ArrayList<String>();//= {"word", "head","hard","hand","part","file","kite","raft"};
	public static int wordsize;
	public static String userword;
	
	public static String removeDuplicatedCharacters(String word) {
		char[] chars = word.toCharArray();
		Arrays.sort(chars);
		word = String.valueOf(chars);
		return word.replaceAll("(.)\\1{1,}", "$1"); //removes consecutive repeated characters in a string
	}
	
	public static String getRandomWord() {
		return words.get(new Random().nextInt(words.size()));
	}
	
	public static int getCommonLetters(String word) {
		if (word.equals(userword)) {
			return WIN;
		}
		word = removeDuplicatedCharacters(word);
		int common = 0;
		for (char c : word.toCharArray()) {
			if (userword.indexOf(c) != -1) {
				common++;
			}
		}
		return common;
	}
	
	public static void shortlistWordsArray(String compword, int commonletters) {
		for (String s : words) {
			for(int i = 0; i < compword.length(); ++i) {
				if (commonletters == 0) {	
				    if (s.contains(String.valueOf(compword.charAt(i)))){
				    	words.remove(s);
				    	break;
				    }
				}
				if (commonletters == 1) {
				    if (s.contains(String.valueOf(compword.charAt(i)))){
				    	break;
				    }
				    if(i == compword.length() - 1) {
				    	words.remove(s);
				    }
				}
			}	
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		
		wordsize = Integer.parseInt(args[0]);
		BufferedReader br = new BufferedReader(new FileReader("sowpods.txt"));
		try {
			String line = br.readLine();
			for (int i = 0; line != null && line.length() == wordsize; line = br
					.readLine(), ++i) {
				words.add(line.toLowerCase());
			}
		} catch (Exception e) {
			System.out.println("Error in reading the file");
			return;
		}
		
		System.out.println("You think a "+wordsize+" lettered word and enter it");
		Scanner scan = new Scanner(System.in);
		userword = scan.next();
		
		String compword = getRandomWord();
		System.out.println("computer guess\n"+compword);
		int i = getCommonLetters(compword);
		
		while (i != WIN) {
			System.out.println(i + " letters in common");
			shortlistWordsArray(compword,i);
			compword = getRandomWord();
			i = getCommonLetters(compword);
		}
		System.out.println("computer won");	
	}
}
