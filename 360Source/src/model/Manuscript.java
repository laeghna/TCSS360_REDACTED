/*****************************************************
 * Group 3: Lisa Taylor, Nathanael Toporek, Anh Tran *
 * TCSS 360, Spring 2016                             *
 * Deliverable #3                                    *
 *****************************************************/

package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
/**
 * Class that will contain a list of manuscripts used in a given conference.
 * @author Nathanael Toporek, nat96@uw.ed
 * @version 1.0.0
 */
public class Manuscript implements Serializable {
	
	
	/**
	 * Object serialization ID used in the object serialization process.
	 */
	private static final long serialVersionUID = -2930385948672657316L;
	/**
	 * Whether or not this paper has been accepted.
	 */
	private boolean myAcceptance;
	/**
	 * The title of this Manuscript.
	 */
	private String myTitle;
	/**
	 * The Author's First and Last Name.
	 */
	private String myAuthor;
	/**
	 * The Pathname to this manuscript's text.
	 */
	private String myPathname;
	/**
	 * The unique username of the owner for this manuscript.
	 */
	private String myOwnersUsername;
	/**
	 * The username of the subprogram for this manuscript.
	 */
	private String mySPC;
	/**
	 * The Subprogram chair's recommendation on this manuscript.
	 */
	private Recommendation myRecommendation;
	/**
	 * An arraylist of reviewers for this manuscript.
	 */
	private ArrayList<Reviewer> myReviewers;
	/**
	 * A map that maps a reviewer to their review for this paper.
	 */
	private HashMap<String, Review> myReviews;
	/**
	 * Constructor for this manuscript class.
	 * @param theTitle The title of this manuscript.
	 * @param theAuthor The author of this manuscript.
	 * @param theAuthorsUsername The unsername for the author of this manuscript.
	 * @param thePathname The pathname to the text of this manuscript.
	 * @throws NullPointerException if any of the above fields are either null or
	 * 								have a length of 0.
	 */
	public Manuscript(String theTitle, String theAuthor,
			String theAuthorsUsername, String thePathname) {
		
		if(theTitle == null || theTitle.length() == 0) {
			throw new NullPointerException("Title is null.");
		} else if (theAuthor == null || theAuthor.length() == 0) {
			throw new NullPointerException("Author is null.");
		} else if (theAuthorsUsername == null || theAuthorsUsername.length() == 0) {
			throw new NullPointerException("Author's username is null.");
		} else if (thePathname == null || thePathname.length() == 0) {
			throw new NullPointerException("Pathname to text is null.");
		}
		
		myTitle = theTitle;
		myAuthor = theAuthor;
		myPathname = thePathname;
		myOwnersUsername = theAuthorsUsername;
		mySPC = null;
		myAcceptance = false;
		myReviewers = new ArrayList<Reviewer>();
		myReviews = new HashMap<String, Review>();
	}
	/**
	 * Alternate constructor for manuscript that makes this null.
	 */
	public Manuscript() {
		
	}
	
	/**
	 * Returns the list of reviewers for this manuscript.
	 * @return myReviewers
	 */
	public ArrayList<Reviewer> getReviewers() {
		return myReviewers;
	}
	/**
	 * Returns an arraylist of reviews for this manuscript.
	 * @return All reviews for this manuscript.
	 */
	public ArrayList<Review> getReviews() {
		ArrayList<Review> res = new ArrayList<Review>();
		
		for(String key : myReviews.keySet()) {
			res.add(myReviews.get(key));
		}
		return res;
	}
	/**
	 * Returns the first/last name of the author.
	 * @return returns the first/last name of the author.
	 */
	public String getAuthor() {
		
		return myAuthor;
	}
	/**
	 * Returns the unique name of this paper's author.
	 * @return Returns the author's username.
	 */
	public String getOwnersUsername() {
		return myOwnersUsername;
	}
	/**
	 * Returns the pathname to the text of this manuscript.
	 * @return
	 */
	public String getPathname() {
		
		return myPathname;
		
	}
	/**
	 * Sets the pathname to the text of this text to a new one.
	 * Used for an author to edit their submission.
	 * @param thePathname The new pathname for the text.
	 * @throws NullPointerException if thePathname == null
	 */
	public void setPathname(String thePathname) {
		
		if (thePathname == null || thePathname.length() == 0) {
			throw new NullPointerException("FRUSTRATED NULLPOINTEREXCEPTION IS " + 
					"TIRED OF YOU BEING RETARDED. >:[");
		}
		
		myPathname = thePathname;
	}
	
	/**
	 * Gets the SPC for this paper's username.
	 * @return Returns this paper's SPC's username.
	 */
	public String getSPCsUsername() {
		
		if (mySPC == null || mySPC.length() == 0) {
			return "This paper has not yet been assigned to a SubProgram Chair. :/";
		}
		return mySPC;
	}
	/**
	 * Sets the SPC for this paper. 
	 * @param theSPC The username for the SPC assigned to this paper.
	 * @throws NullPointerException if theSPC == null
	 * @throws SecurityException if theSPC == myOwnersUsername.
	 */
	public void setSPCsUsername(String theSPC) {
		
		if(theSPC == null || theSPC.length() == 0) {
			throw new NullPointerException("String is null, bro.");
		} else if (theSPC.equals(myOwnersUsername)) {
			throw new SecurityException("SPC IS AUTHOR, DENIED.");
		}
		mySPC = theSPC;
	}
	/**
	 * Gets the title of this manuscript.
	 * @return The Title of this manuscript.
	 */
	public String getTitle() {
		
		return myTitle;
	}
	public Set<String> getReveiwersUsernames() {
		
		return myReviews.keySet();
	}
	/**
	 * Gets whether or not this paper has been accepted.
	 * @return returns whether or not this paper has been accepted.
	 */
	public boolean getAcceptance() {
		return myAcceptance;
	}
	
	/**
	 * Sets the acceptance for this paper.
	 * @param theAcceptance Whether or not the paper was accepted.
	 */
	public void setAcceptance(boolean theAcceptance) {
		myAcceptance = theAcceptance;
	}
	
	/**
	 * Gets the recommendation for this manuscript.
	 * @return Returns the recommendation for this manuscript.
	 * @throws NullPointerException if theRecommendation == null
	 */
	public Recommendation getRecommendation() {
		if(myRecommendation == null) {
			throw new NullPointerException("Recommendation is null, bro.");
		}
		return myRecommendation;
	}
	/**
	 * Sets the recommendation status for this Manuscript.
	 * @param theRec The recommendation for this Manuscript.
	 * @throws NullPointerException if theRec == null
	 * 		   SecurityException if mySPC == null
	 */
	public void setRecommendation(Recommendation theRec) {
		if(theRec == null) {
			throw new NullPointerException("Recommendation is null, bro.");
		} else if (mySPC == null) {
			throw new SecurityException("A subprogram chair must be assigned before " + 
					"a recommendation may be submitted.");
		}
		
		myRecommendation = theRec;
	}
	/**
	 * Returns a string that describes the number of reviews completed out of
	 * the total number assigned.
	 * @return
	 */
	public String getReviewStatus() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(myReviews.keySet().size() + 
				" reviews completed out of " + 
				myReviewers.size() + 
				" reviews assigned.");
		
		return sb.toString();
	}
 	/**
 	 * Adds a new review to the list of reviews for this manuscript.
 	 * @param theReviewer The reviewer submitting theReview
 	 * @param theReview The Review that is being submitted.
 	 * @throws NullPointerException if theReviewer == null || theReview == null;
 	 * 		   SecurityException if theReviewer is not assigned to this paper.
 	 * 		   SecurityException if the owner of theReview is not theReviewer.
 	 * 		   IllegalArgumentException if there is already a review submitted by
 	 * 									theReviewer.
 	 */
	public void addNewReview(Reviewer theReviewer, Review theReview) {
		
		if(theReviewer == null || theReview == null) {
			throw new NullPointerException("One or more args are null.");
		} else if(!myReviewers.contains(theReviewer)) {
			throw new SecurityException("You are not allowed to submit a review for this paper.");
		} else if (!theReviewer.getUsername().equals(theReview.getReviewer())) {
			throw new SecurityException("Inconsistency between review and reviewer.");
		} else if(myReviews.containsKey(theReviewer)) {
			throw new IllegalArgumentException("Review already exists for this reviewer.");
		}
		
		myReviews.put(theReviewer.getUsername(), theReview);
	}
	
	/**
	 * Removes the review associated with theReviewer from the map of reviews.
	 * @param theReviewer the Review being removed.
	 * @throws NullPointerException if theReviewer == null
	 * 		   SecurityException if theReviewer is not assigned to this paper.
	 * 		   IllegalArgumentException if !myReviews.containsKey(theReveiwer)
	 */
	public void removeReview(Reviewer theReviewer) {
		
		if(theReviewer == null) {
			throw new NullPointerException("The reviewer is null.");
		} else if(!myReviewers.contains(theReviewer)) {
			throw new SecurityException("You are not allowed to edit a review for this paper.");
		} else if(!myReviews.containsKey(theReviewer)) {
			throw new IllegalArgumentException("NO REVEIW SUBMITTED BY " +
					theReviewer.getUsername() + "! STOP. BEING. STUPID. GHAAAAAAAAA!!!!!!");
		}
		
		myReviews.remove(theReviewer);
	}
	/**
	 * Changes a review, used so that a reviewer may edit their review for this paper.
	 * @param theReviewer The reviewer doing the changes.
	 * @param theReview The review theReviewer wants to submit.
	 * @throws SecurityException if theReviewer isn't reviewing this paper.
	 * 		   NullPointerException if theReviewer == null || theReview == null
	 * 		   IllegalArgumentException if theReviewer hasn't submitted a review.
	 */
	public void changeReview(Reviewer theReviewer, Review theReview) {
		
		if(theReviewer == null) {
			throw new NullPointerException("The reviewer is null.");
		} else if (theReview == null) {
			throw new NullPointerException("The review is null.");
		} else if (!myReviewers.contains(theReviewer)) {
			throw new SecurityException("You are not allowed to edit a review for this paper.");
		} else if (!myReviews.containsKey(theReviewer)) {
			throw new IllegalArgumentException("NO REVEIW SUBMITTED BY " +
					theReviewer.getUsername() + "! STOP. BEING. STUPID. GHAAAAAAAAA!!!!!!");
		} 
		
		myReviews.put(theReviewer.getUsername(), theReview);
	}
	/**
	 * Adds a reviewer to the list of reviewers for this paper.
	 * @param theReviewer The reviewer to be added.
	 * @throws SecurityException if mySPC == null || the reviewer in question is the author.
	 */
	public void addReviewer(Reviewer theReviewer) {
		
		if(theReviewer == null) {
			throw new NullPointerException("Proposed reviewer is null.");
		} else if (mySPC == null){
			throw new SecurityException("Please assign a subprogram chair this paper " +
					"assigning it reviewers.");
		} else if(theReviewer.getUsername().equals(myOwnersUsername)) {
			throw new SecurityException("PROPOSED REVIEWER IS AUTHOR. DENIED.");
		}
		
		myReviewers.add(theReviewer);
	}
	/**
	 * Prints out a string representation of this manuscript that a program chair could
	 * get an at a glance view. 
	 * @return A string representation of this object.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("TITLE: " + myTitle);
		sb.append("\nAUTHOR: " + myAuthor);
		sb.append("\nPATHNAME: " + myPathname);
		
		if(mySPC != null) {
			
			sb.append("\nSUBPROGRAM CHAIR: " + mySPC);
			if(myReviewers.size() > 0) {
				
				sb.append("\nREVIEWERS: " + myReviewers.get(0).getUsername());
				for(int i = 1; i < myReviewers.size(); i++) {
					sb.append(", " + myReviewers.get(i).getUsername());
				}
				
				sb.append("\nREVIEW STATUS: " + getReviewStatus());
				
			} else {
				
				sb.append("\nREVIEWERS: NONE" + 
						"\nREVIEW STATUS: N/A");				
			}
		} else {
			sb.append("\nSUBPROGRAM CHAIR: NONE" +
					"\nREVIEWERS: NONE"  + 
					"\nREVIEW STATUS: N/A");
		}
		if(myRecommendation != null) {
			sb.append("\nRECOMMENDATION STATUS: SUBMITTED");
		} else {
			sb.append("\nRECOMMENDATION STATUS: PENDING");
		}
		
		if(myAcceptance == true) {
			sb.append("\nACCEPTANCE: ACCEPTED");
		} else {
			sb.append("\nACCEPTANCE: DENIED");
		}
		
		return sb.toString();
	}
}
