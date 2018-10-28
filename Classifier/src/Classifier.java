import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Classifier {
	
	private static final String BLACKLIST_NAME = "blacklist.txt";
	private Set<String> blacklist;
	private Set<Integer> asciiSpecial;
	
	public Classifier(){
		generateBlacklist();
		makeSpecialSet();
	}
	
	private void generateBlacklist() {
		
		Set<String> blacklistMap = new HashSet<String>();
		
		BufferedReader br = null;
		FileReader fr = null;

		try {
			fr = new FileReader(BLACKLIST_NAME);
			br = new BufferedReader(fr);
			String currentLine;
			while ((currentLine = br.readLine()) != null) {
				blacklistMap.add(currentLine);
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
		this.blacklist = blacklistMap;
	}
	
	public boolean isInBlacklist(String password) {	
		return blacklist.contains(password);
	}
	
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
	
	private boolean checkComprehensive(String pwd) {
		boolean hasLower = false;
		boolean hasUpper = false;
		boolean hasNumber = false;
		boolean hasSpecial = false;

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
			
		return hasLower && hasUpper && hasNumber && hasSpecial;
	}
	
	public String strengthCheck(String password) {
		if(isInBlacklist(password)) {
			System.out.println("is in BlackList");
			return "weak";
		}
		if(password.length() >15) return "strong";
		if(!checkComprehensive(password)) {
			System.out.println("is not comprehensive");
			return "weak";
		}
		
		return "strong";
		
	}
	
	public static void main(String[] args) {
		Classifier c = new Classifier();
		
		System.out.println(c.strengthCheck("dragon"));
		System.out.println(c.strengthCheck("iloveyou"));
		System.out.println(c.strengthCheck("password"));
		System.out.println(c.strengthCheck("bigbang"));
		System.out.println(c.strengthCheck("snekf"));
		System.out.println(c.strengthCheck("123456"));
		System.out.println(c.strengthCheck("2984borawQ!"));
		System.out.println(c.strengthCheck("iloveyou"));

	}
}
