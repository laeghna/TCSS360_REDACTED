/*****************************************************
 * Group 3: Lisa Taylor, Nathanael Toporek, Anh Tran *
 * TCSS 360, Spring 2016                             *
 * Deliverable #2                                    *
 *****************************************************/

package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import model.Conference;
import model.RegisteredUser;

/** Runs the UI for the application.
 * 
 * @author Nathanael Toporek, nat96@uw.edu
 * @version 7 May 2016
 */
public class Driver {
    
    /** Maps each registered user's unique username to its corresponding RegisteredUser object. */
    private static HashMap<String, RegisteredUser> registeredUsers;
    
    private static ArrayList<Conference> conferences;
    
    /** Main method starts the program. Command line arguments are ignored. */
    public static void main(String[] args) {
    	
    	readSerializedData();
    	
    	GeneralUI loginScreen = new GeneralUI(conferences, registeredUsers);
    	
    	loginScreen.displayDefaultMenu();
    	
    	writeSerializableData();
    }
    
    
    @SuppressWarnings("unchecked")
	private static void readSerializedData() {
    	
    	FileInputStream confIn = null;
    	FileInputStream userIn = null;
    	try {
			confIn = new FileInputStream(".ser/confs.ser");
			userIn = new FileInputStream(".ser/usermap.ser");	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    	ObjectInputStream confRead = null;
    	ObjectInputStream userRead = null;
    	try {
			confRead = new ObjectInputStream(confIn);
			userRead = new ObjectInputStream(userIn);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	try {
			conferences = (ArrayList<Conference>) confRead.readObject();
			registeredUsers = (HashMap<String, RegisteredUser>) userRead.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
    private static void writeSerializableData() {
    	
    	FileOutputStream confOut = null;
    	FileOutputStream userOut = null;
    	try {
			confOut = new FileOutputStream(".ser/confs.ser");
			userOut = new FileOutputStream(".ser/usermap.ser");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    	ObjectOutputStream confWrite = null;
    	ObjectOutputStream userWrite = null;
    	try {
			confWrite = new ObjectOutputStream(confOut);
			userWrite = new ObjectOutputStream(userOut);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	try {
			confWrite.writeObject(conferences);
			userWrite.writeObject(registeredUsers);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
