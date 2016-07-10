import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MasterMindUser {
	final static int WIN = -1;

	public static String[] words;// = {"word", "head","hard","hand","part","file","kite","raft"};
	public static int wordsize;
	public static String computerword;

	private static String removeDuplicatedCharacters(String word) {
		char[] chars = word.toCharArray();
		Arrays.sort(chars);
		word = String.valueOf(chars);
		return word.replaceAll("(.)\\1{1,}", "$1");
	}

	private static String getRandomWord() {
		int randomindex = new Random().nextInt(words.length);
		while (hasDuplicatedCharacters(words[randomindex])) {
			randomindex = new Random().nextInt(words.length);
		}
		return words[randomindex];
	}

	public static boolean hasDuplicatedCharacters(String word) {
		for (char c : word.toCharArray()) {
			if (word.indexOf(c) != word.lastIndexOf(c)) {
				return true;
			}
		}
		return false;
	}

	public static int getCommonLetters(String word) {
		if (word.equals(computerword)) {
			return WIN;
		}
		word = removeDuplicatedCharacters(word);
		int common = 0;
		for (char c : word.toCharArray()) {
			if (computerword.indexOf(c) != -1) {
				common++;
			}
		}
		return common;
	}

	public static void main(String[] args) throws FileNotFoundException {

		wordsize = Integer.parseInt(args[0]);
		BufferedReader br = new BufferedReader(new FileReader("sowpods.txt"));
		try {
			String line = br.readLine();
			for (int i = 0; line != null && line.length() == wordsize; line = br
					.readLine(), ++i) {
				words[i] = line.toLowerCase();
			}
		} catch (Exception e) {
			System.out.println("Error in reading the file");
			return;
		}

		computerword = getRandomWord();
		System.out.println("Computer has a word\nEnter your guess");

		Scanner scan = new Scanner(System.in);
		int i = getCommonLetters(scan.next().toLowerCase());

		while (i != WIN) {
			System.out.println(i + " letters in common");
			i = getCommonLetters(scan.next().toLowerCase());
		}
		System.out.println("you won");
	}
}