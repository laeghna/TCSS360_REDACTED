/*****************************************************
 * Group 3: Lisa Taylor, Nathanael Toporek, Anh Tran *
 * TCSS 360, Spring 2016                             *
 * Deliverable #2                                    *
 *****************************************************/

package view;

import java.util.HashMap;

import model.RegisteredUser;

/** Runs the UI for the application.
 * 
 * @author Lisa Taylor
 * @version 7 May 2016
 */
public class Main {
    
    /** Maps each registered user's unique username to its corresponding RegisteredUser object. */
    private HashMap<String, RegisteredUser> registeredUsers = null;
    
    /** The current registered user. */
    private RegisteredUser currUser = null;
    
    /**
     * Private constructor to prevent instantiation of this class.
     */
    private Main() {
        //Nothing to see here
    }

    /** Main method starts the program. Command line arguments are ignored. */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
