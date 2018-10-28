import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Classifier {
	
	private static final String BLACKLIST_NAME = "blacklist.txt";
	private Set<String> blacklist;
	
	public Classifier(){
		generateBlacklist();
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
	
	public String strengthCheck(String password) {
		if(isInBlacklist(password)) return "weak";
		if(password.length() >15) return "strong";
		//if(!checkComprehensive(password)) return "weak";
		
		return "strong";
		
	}
	

	public static void main(String[] args) {
		Classifier c = new Classifier();
		
		System.out.println(c.isInBlacklist("dragon"));
		System.out.println(c.isInBlacklist("iloveyou"));
		System.out.println(c.isInBlacklist("password"));
		System.out.println(c.isInBlacklist("bigbang"));
		System.out.println(c.isInBlacklist("snekf"));
	}

}
