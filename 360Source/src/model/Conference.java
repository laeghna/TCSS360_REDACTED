/* Nathanael Alan Toporek
 * TCSS 325, Spring 2016
 * Deliverable #2
 */

package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Container class for information about a singular conference.
 * @author Nathanael Toporek, nat96@uw.edu
 * @version 1.0.0
 */
public class Conference implements Serializable {
	
	/**
	 * Object UID for serialization.
	 */
	private static final long serialVersionUID = -5002295190798265670L;

	/**
	 * The name of this conference.
	 */
	private String myConferenceName;
	
	/**
	 * The ProgramChair object for this conference.
	 */
	private ProgramChair myProgramChair;
	
	/**
	 * The submission deadline for this conference.
	 */
	private Date mySubmissionDeadline;
	/**
	 * The review deadline for this conference.
	 */
	private Date myReviewDeadline;
	/**
	 * The recommendation deadline for this conference.
	 */
	private Date myRecommendationDeadline;
	
	/**
	 * A hashmap of usernames to reviewers, used to determine if a user is a 
	 * reviewer.
	 */
	private HashMap<String, Reviewer> myReviewers;
	/**
	 * A hashmap of usernames to subprogram chairs, user to 
	 * determine if a user is a subprogramchair.
	 */
	private HashMap<String, SubprogramChair> mySubprogramChairs;
	
	/**
	 * A list of manuscripts being presented at this conference.
	 */
	private ArrayList<Manuscript> myManuscripts;
	
	/**
	 * Constructor for this Conference.
	 * 
	 * @param theConferenceName The name for this conference
	 * @param theProgramChair The ProgramCHair for this conference.
	 * @param theSubmissionDeadline The submission deadline for this conference.
	 * @param theReviewDeadline The review deadline for this conference.
	 * @param theRecommendationDeadline The recommendation deadline for this conference.
	 * 
	 * @throws NullPointerException if any params are null.
	 * @throws IllegalArgumentException if any of the dates have already expired.
	 */
	public Conference(String theConferenceName,
					  ProgramChair theProgramChair, 
					  Date theSubmissionDeadline,
					  Date theReviewDeadline,
					  Date theRecommendationDeadline) throws NullPointerException, 
															 IllegalArgumentException{
		
		if(theConferenceName == null ||
		   theProgramChair == null ||
		   theSubmissionDeadline == null ||
		   theReviewDeadline == null || 
		   theRecommendationDeadline == null) {
			
			throw new NullPointerException("One or more args are null. Fix yo shit.");
		} 
		
		Date now = new Date();
		now.setTime(System.currentTimeMillis());
		
		if(theSubmissionDeadline.before(now) ||
		   theReviewDeadline.before(now) ||
		   theRecommendationDeadline.before(now)) {
			
			throw new IllegalArgumentException("One or more deadlines have already past!");
		}
		
		myConferenceName = theConferenceName;
		
		myProgramChair = theProgramChair;
		
		mySubmissionDeadline = (Date) theSubmissionDeadline.clone();
		myReviewDeadline = (Date) theReviewDeadline.clone();
		myRecommendationDeadline = (Date) theRecommendationDeadline.clone();
		
		myReviewers = new HashMap<String, Reviewer>();
		mySubprogramChairs = new HashMap<String, SubprogramChair>();
		
		myManuscripts = new ArrayList<Manuscript>();
	}
	
	/**
	 * Returns the program chair for this conference.
	 * @return Reutrns the program chir for this conference.
	 */
	public ProgramChair getProgramChair() {
		
		return myProgramChair;
	}
	
	/**
	 * Gets the subprogram chair associated with the passes username.
	 * @param theirUsername The username of the program chair we're searching for.
	 * @return Returns the associated subprogram chair if they're assigned for this conference.
	 * 		   Returns null if else.
	 */
	public SubprogramChair getSubProgramChair(String theirUsername) {
		
		if(mySubprogramChairs.containsKey(theirUsername)) {
			return mySubprogramChairs.get(theirUsername);
		} else {
			return null;
		}
	}
	
	/**
	 * Returns all of the manuscripts that this conference contains.
	 * @return All manuscripts for this conference.
	 */
	public ArrayList<Manuscript> getManuscripts() {
		
		return myManuscripts;
	}
	/**
	 * Returns a sublist of the manuscripts, all of which are associated with the
	 * passed username.
	 * @param theAuthorsUsername The username of the person's papers we're grabbing. 
	 * @return Returns the list of manuscripts associated with theAuthorsUsername, in the form
	 * 		   of an ArrayList.
	 * @throws NullPointerException if theAuthorsUsername == null || theAuthorsUsername.length == 0
	 */
	public ArrayList<Manuscript> getMyManuscripts(String theAuthorsUsername) 
			throws NullPointerException{
			
		if(theAuthorsUsername == null || theAuthorsUsername.length() == 0) {
			throw new NullPointerException("Passed username is null.");
		}
		
		ArrayList<Manuscript> res = new ArrayList<Manuscript>();
		
		for(Manuscript man : myManuscripts) {
			
			if(man.getOwnersUsername().equals(theAuthorsUsername)) {
				res.add(man);
			}
		}
		
		return res;
	}
	/**
	 * Returns the submission deadline.
	 * @return The date of the submission deadline.
	 */
	public Date getSubmissionDeadline() {
		
		return (Date) mySubmissionDeadline.clone();
	}
	
	/**
	 * Returns the review deadline.
	 * @return The date of the review deadline. 
	 */
	public Date getReviewDeadline() {
		
		return (Date) myReviewDeadline.clone();
	}
	
	/**
	 * Returns the recommendation deadline.
	 * @return The date of the recommendation deadline.
	 */
	public Date getRecommendationDeadline() {
		
		return (Date) myRecommendationDeadline.clone();
	}
	/**
	 * Adds a manuscript to this conference.
	 * @param theManuscript The manuscript to be added.
	 * @throws NullPointerException If any parameter is null.
	 * @throws IllegalArgumentException If the submission deadline has past.
	 */
	public void addManuscript(Manuscript theManuscript) 
			throws NullPointerException, IllegalArgumentException {
		
		if(theManuscript == null) {
			throw new NullPointerException("Paper is null.");
		} else if(new Date().compareTo(mySubmissionDeadline) >= 0) {
			throw new IllegalArgumentException("Submission Deadline has past.");
		}
		
		myManuscripts.add(theManuscript);
	}
	/**
	 * Assigns a registered user to the role of reviewer.
	 * @param theUser The User to become a reviewer.
	 * @throws NullPointerException If theUser == null.
	 * @throws IllegalArgumentException If myReviewers.containsKey(theUser.getUsername())
	 */
	public void assignReviewer(RegisteredUser theUser) 
			throws NullPointerException, IllegalArgumentException{
		
		if(theUser == null) {
			throw new NullPointerException("User is null.");
		} else if(myReviewers.containsKey(theUser.getUsername())) {
			throw new IllegalArgumentException("User is already a reviewer.");
		}
		
		String str = theUser.getUsername();
		myReviewers.put(str, new Reviewer(str));
	}
	/**
	 * Assigns a registered user to the role of Subprogram chair for this conference.
	 * @param theUser The user to become a subprogram chair.
	 * @throws NullPointerException If theUser == null
	 * @throws IllegalArgumentException If mySubprogramChairs.containsKey(theUser.getUsername())
	 */
	public void assignSubprogramChair(RegisteredUser theUser) 
			throws NullPointerException, IllegalArgumentException{
		
		if(theUser == null) {
			throw new NullPointerException("User is null");
		} else if(mySubprogramChairs.containsKey(theUser.getUsername())) {
			throw new IllegalArgumentException("User is already a Subprogram Chair.");
		}
		
		String str = theUser.getUsername();
		mySubprogramChairs.put(str, new SubprogramChair(str));
	}
	 /**
	  * Returns an arraylist of enum type ROLE.
	  * @param theUsername The username of the user that we're searching 
	  * @return Returns the roles of the specified user in the form of an ArrayList.
	  */
	public ArrayList<Role> getRoles(String theUsername) {
		
		ArrayList<Role> theirRoles = new ArrayList<Role>();
		
		if(theUsername.equals(myProgramChair.getUsername())) {
			theirRoles.add(Role.PROGRAMCHAIR);
		}
		if(myReviewers.containsKey(theUsername)) {
			theirRoles.add(Role.REVIEWER);
		}
		if(mySubprogramChairs.containsKey(theUsername)) {
			theirRoles.add(Role.SUBPROGRAMCHAIR);
		}
		for(Manuscript m : myManuscripts) {
			
			if(m.getOwnersUsername().equals(theUsername)) {
				theirRoles.add(Role.AUTHOR);
			}
		}
 		
		return theirRoles;
	}
	/**
	 * Returns a string representation of this conference.
	 */
	public String toString() {
		return myConferenceName;
	}
}
