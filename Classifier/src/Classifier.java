import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/***
 * Classifier class that determines the strength of a password.
 * Reads a file to generate a blacklisted words, and checks the
 * composition of the given password to determine the strength.
 * 
 * @author David Sonul Choi and Thuan Nguyen
 *
 */
public class Classifier {
	
	//name of the file that contains words that should be blacklisted
	private static final String BLACKLIST_NAME = "blacklist.txt";
	private Set<String> blacklist; //Set of blacklisted words
	private Set<Integer> asciiSpecial; //set of special characters
	
	/***
	 * Constructor class that initializes the Classifier
	 */
	public Classifier(){
		
		//initializes blacklist and asciiSpecial sets.
		generateBlacklist();
		makeSpecialSet();
	}
	
	/***
	 * Initializes the blacklist set by reading input from a txt file
	 */
	private void generateBlacklist() {
		
		Set<String> blacklistSet = new HashSet<String>();
		
		BufferedReader br = null;
		FileReader fr = null;

		try {
			fr = new FileReader(BLACKLIST_NAME);
			br = new BufferedReader(fr);
			String currentLine;
			
			//read a txt file and generate a set accordingly
			while ((currentLine = br.readLine()) != null) {
				blacklistSet.add(currentLine);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		//initialize the blacklist set
		this.blacklist = blacklistSet;
	}
	
	/***
	 * Simple function to check if a password is included in the 
	 * blacklist set.
	 * 
	 * @param password password to check if it is in the blacklist set
	 * @return true if the given password is in the blacklist set
	 * 		   and false otherwise
	 */
	public boolean isInBlacklist(String password) {	
		return blacklist.contains(password);
	}
	
	

	/***
	 * Initializes the asciiSpecial set which contains the set of 
	 * special characters.
	 */
	private void makeSpecialSet() {
		asciiSpecial = new HashSet<Integer>();
		
		for (int i=32; i<=47; i++) {
			asciiSpecial.add(i);
		}
		
		for (int i=58; i<=64; i++) {
			asciiSpecial.add(i);
		}
		
		for (int i=91; i<=96; i++) {
			asciiSpecial.add(i);
		}
		
		for (int i=123; i<=126; i++) {
			asciiSpecial.add(i);
		}
		
		for (int i=128; i<=255; i++) {
			asciiSpecial.add(i);
		}
	}
	
	/***
	 * Checks whether a passwords is comprehensive. A password is
	 * comprehensive if it contains a lower case letter, an upper case
	 * letter, a digit, and a special character and is at least of length 8.
	 * 
	 * @param pwd
	 * @return
	 */
	private boolean checkComprehensive(String pwd) {
		boolean hasLower = false;
		boolean hasUpper = false;
		boolean hasNumber = false;
		boolean hasSpecial = false;
		boolean isOverLength8 = false;

		//checks if the password length is at least 8
		if(pwd.length()>7) {
			isOverLength8 = true;
		}
		
		//checks other conditions
		for(int i=0; i<pwd.length(); i++) {
			int ascii = (int) pwd.charAt(i);
			
			if (!hasLower && ascii >= 97 && ascii <= 122) {
				hasLower = true;
			} if (!hasUpper && ascii >= 65 && ascii <= 90) {
				hasUpper = true;
			} if (!hasNumber && ascii >= 48 && ascii <= 57) {
				hasNumber = true;
			} if (!hasSpecial && asciiSpecial.contains(ascii)) {
				hasSpecial = true;
			}
		}
			
		return hasLower && hasUpper && hasNumber && hasSpecial && isOverLength8;
	}
	
	/***
	 * Checks the strength of a given password and prints out
	 * the result.
	 * 
	 * @param password password to check the strength of
	 * @return "weak" if the password weak, and "strong" if the password is strong
	 */
	public String strengthCheck(String password) {
		//return weak if the password is in the blacklist
		if(isInBlacklist(password)) {
			return "weak";
		}
		
		//return strong if the password is at least of length 16
		if(password.length() >15) {
			return "strong";
		}
		
		//return weak if the password is not comprehensive
		if(!checkComprehensive(password)) {
			return "weak";
		}
		
		//return strong otherwise
		return "strong";
		
	}
	
	/***
	 * main function that initializes the Classifier and takes in
	 * a user input to check the strength of the user given password
	 * 
	 * @param args not used in this program
	 */
	public static void main(String[] args) {
		
		//create a Classifier
		Classifier c = new Classifier();
		
		//take in user given password and print out the result
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		while(true) {
			System.out.println(c.strengthCheck(input));
			input = scan.nextLine();
		}
	}
}
