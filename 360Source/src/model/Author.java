/*****************************************************
 * Group 3: Lisa Taylor, Nathanael Toporek, Anh Tran *
 * TCSS 360, Spring 2016                             *
 * Deliverable #3                                    *
 *****************************************************/

package model;

import java.io.Serializable;
/**
 * Class that contains the functionalities of an Author.
 * @author Nathanael Toporek, nat96@uw.edu
 * @version 1.0.0
 */
public class Author implements Serializable {
	
	
	private static final long serialVersionUID = 946681079153846151L;
	/**
	 * the username of this Author.
	 */
	private String myUsername;
	
	/**
	 * Constructor for this Author class.
	 * Will take the passed username and initialize it ot this author's 
	 * username.
	 * @param theUsername the username of the user than just so happens to be this
	 * 		  author.
	 */
	public Author(String theUsername) {
		
		myUsername = theUsername;
	}
	
	
	/**
	 * Allows a user to edit the path of their paper, to manage
	 * the editing process.
	 * @param theManuscript The manuscript bring edited.
	 * @param thePathname The pathname to the new manuscript.
	 * @throws NullPointerException if theManuscript == null || thePathname == null
	 * @throws SecurityException if myUsername != theManuscript.getOwnersUsername()
	 */
	public void editManuscript(Manuscript theManuscript, String thePathname) {
		
		if(theManuscript == null) {
			throw new NullPointerException("Manuscript is null, my dude.");
		} else if(thePathname.length() == 0 || thePathname == null) {
			throw new NullPointerException("Ya string's null or empty, my dude.");
		} else if(!(theManuscript.getOwnersUsername().equals(myUsername))) {
			throw new SecurityException("SARCASTIC EXCEPTION SAYS YOU SHOULD NOT BE DOING THIS.");
		}
		
		theManuscript.setPathname(thePathname);
	}
	/**
	 * Allows a user to remove their paper from the conference.
	 * @param theManuscript The manuscript bring edited.
	 * @throws NullPointerException if theManuscript == null
	 * @throws SecurityException if myUsername != theManuscript.getOwnersUsername()
	 */
	public void removeManuscript(Manuscript theManuscript) {
		
		if(theManuscript == null) {
			throw new NullPointerException("This manuscript is non-existent, my dude.");
		} else if(!(theManuscript.getOwnersUsername().equals(myUsername))) {
			throw new SecurityException("SARCASTIC EXCEPTION SAYS YOU SHOULD NOT BE DOING THIS.");
		}
		
		theManuscript = null;
	}
}
