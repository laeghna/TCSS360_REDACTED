/* Nathanael Toporek
 * TCSS 325, Spring 2016
 */
package tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import model.Conference;
import model.ProgramChair;
import model.RegisteredUser;
/**
 * A class designed to write out a set of initial conditions for
 * our system.
 * @author Nathanael Toporek, nat96@uw.edu
 * @version 1.0.0
 */
public class InfoGen {

	
	public static void main(String... theArgs) {
		
		String[] fnames = {"John","Jane","Nicholai","Thomas", "George",
				"Nicholas", "Lucifer", "Jesus", "FirstName", "Funny"};
		String[] lnames = {"Smith", "Smith", "Tesla", "Jefferson", "Curious",
				"Cage", "Saint", "Christ", "Lastname", "Joke"};
		String[] unames = {"JohnSmith", "JaneSmith", "NTesla", "TJefferson", "CuriousGeorge",
				"God", "Satan", "Messiah", "Username", "Punchline"};
		
		String[] cnames = {"All About Potatoes!", "Not About Potatos. :[", "[REDACTED]"};
		
		HashMap<String, RegisteredUser> Map = new HashMap<String, RegisteredUser>();
		ArrayList<Conference> conferences = new ArrayList<Conference>();
		
		for(int i = 0; i < fnames.length; i++) {
			
			String key = unames[i];
			RegisteredUser ru = new RegisteredUser(unames[i], fnames[i], lnames[i]);
			
			Map.put(key, ru);
		}
		
		for(int i = 0; i < cnames.length; i++) {
			
			Date Deadlines = new Date();
			Deadlines.setTime(System.currentTimeMillis() + 31556926000L);
			ProgramChair pc = new ProgramChair(unames[i], new ArrayList<String>());
			
			Conference c = new Conference(cnames[i], pc, Deadlines, Deadlines, Deadlines);
			conferences.add(c);
		}
		
		FileOutputStream userOut = null;
		FileOutputStream confOut = null;
		try {
			userOut = new FileOutputStream(".ser/usermap.ser");
			confOut = new FileOutputStream(".ser/confs.ser");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ObjectOutputStream oOut = null;
		ObjectOutputStream cOut = null;
		try {
			oOut = new ObjectOutputStream(userOut);
			cOut = new ObjectOutputStream(confOut);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			oOut.writeObject(Map);
			cOut.writeObject(conferences);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
