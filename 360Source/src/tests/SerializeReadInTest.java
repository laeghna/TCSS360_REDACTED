/*****************************************************
 * Group 3: Lisa Taylor, Nathanael Toporek, Anh Tran *
 * TCSS 360, Spring 2016                             *
 * Deliverable #3                                    *
 *****************************************************/

package tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import model.Conference;
import model.RegisteredUser;

/**
 * A class to test that InfoGen.java did its job
 * correctly.
 * @author Nathanael Toporek, nat96@uw.edu
 *
 */
public class SerializeReadInTest {

	@SuppressWarnings("unchecked")
	public static void main(String... theArgs) {
		
		FileInputStream confIn = null;
		FileInputStream userIn = null;
		try {
			confIn = new FileInputStream(".ser/confs.ser");
			userIn = new FileInputStream(".ser/usermap.ser");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ObjectInputStream readConfs = null;
		ObjectInputStream readUsers = null;
		
		try {
			readConfs = new ObjectInputStream(confIn);
			readUsers = new ObjectInputStream(userIn);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ArrayList<Conference> confs = null;
		HashMap<String, RegisteredUser> users = null;
		try {
			confs = (ArrayList<Conference>) readConfs.readObject();
			users = (HashMap<String, RegisteredUser>) readUsers.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("CONFERENCES:");
		for(Conference c : confs) {
			System.out.println(c);
		}
		System.out.println("\n\nUSERS: ");
		for(String key : users.keySet()) {
			System.out.println(users.get(key));
		}
	}
}
