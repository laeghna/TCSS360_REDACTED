package model;
import java.io.Serializable;

/**
 * Reviewer class. Defines the role of a Reviewer.
 * @author Anh Tran
 * @version 05/06/2016
 */
public class Reviewer implements Serializable{

    //For Serialization only
    private static final long serialVersionUID = -1242844363061411863L;
    /**
     * Username of Reviewer
     */
    private String userName;

    /**
     * Constructor. Instantiates class with username and roles.
     * @param newUserName username of Reviewer
     * @exception NullPointerException if newUserName is null
     */
    public Reviewer(String newUserName) {
        if(newUserName == null || newUserName.equals("")) {
            throw new NullPointerException("Invalid Username");
        }
        userName = newUserName;
    }

    /**
     * Submits review of Reviewer
     * @param review The Reviewer's review of a Manuscript
     * @return Returns review submitted. Will be stored and handled in main
     * @exception NullPointerException if no review exists or is found (null)
     */
    public String submitReview(String review) {
        if(review == null || review.equals("")) {
            throw new NullPointerException("No review found");
        }
        return review;
    }

    /**
     * Resubmits a new  or edited review
     * @param review The Reviewer's new or edited review of a Manuscript
     * @return Returns review submitted. Will be stored and handled in main
     */
    public String changeReview(String review) {
        return submitReview(review);
    }

    /**
     * Unsubmits a review belonging to Reviewer
     * @return Returns null review. Will be stored and handled in main
     */
    public String unSubmitReview() {
        return null;
    }

    /**
     * Getter. Gets username of Reviewer.
     * @return The username of Reviewer
     */
    public String getUsername() {
        return userName;
    }


}
