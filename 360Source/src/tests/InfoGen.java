/*****************************************************
 * Group 3: Lisa Taylor, Nathanael Toporek, Anh Tran *
 * TCSS 360, Spring 2016                             *
 * Deliverable #3                                    *
 *****************************************************/

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
		
		String[] pcfn = {"Anh", "Nathan", "Lisa"};
		String[] pcln = {"Tran", "Toporek", "Taylor"};
		String[] pcun = {"lordandy", "nat96", "laeghna"};
		
		String[] cns = {"9th European Conference on Intellectual Capital",
					    "12th International Conference on Cyber Warfare and Security",
						"15th European Conference on eLearning"};
		
		HashMap<String, RegisteredUser> Map = new HashMap<String, RegisteredUser>();
		ArrayList<Conference> conferences = new ArrayList<Conference>();
		
		for(int i = 0; i < cns.length; i++) {
			
			String key = pcun[i];
			RegisteredUser ru = new RegisteredUser(pcun[i], pcfn[i], pcln[i]);
			
			Map.put(key, ru);
		}
		
		for(int i = 0; i < cns.length; i++) {
			
			Date Deadlines = new Date();
			Deadlines.setTime(System.currentTimeMillis() + 31556926000L);
			ProgramChair pc = new ProgramChair(pcun[i], new ArrayList<String>());
			
			if(i == 0) {
				Deadlines = new Date(System.currentTimeMillis() + 1L);
			}
			
			Conference c = new Conference(cns[i], pc, Deadlines, Deadlines, Deadlines);
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
