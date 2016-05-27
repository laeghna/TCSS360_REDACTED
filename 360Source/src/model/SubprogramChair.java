/*****************************************************
 * Group 3: Lisa Taylor, Nathanael Toporek, Anh Tran *
 * TCSS 360, Spring 2016                             *
 * Deliverable #3                                    *
 *****************************************************/

package model;
import java.io.Serializable;

import enums.Recommendation;

/**
 * @author Anh Tran
 * @version 05/27/2016
 */
public class SubprogramChair implements Serializable{

	private static final long serialVersionUID = -5435723955102272844L;
	/**
     * Max amount of manuscripts assigned to Subprogram Chair
     */
    public static final int MAXPAPERS = 4;

    //Username of subprogram chair
    private String userName;

    /**
     * Creates a subprogram chair role.
     * @param newUserName Username of Subprogram Chair
     * @exception NullPointerException if newUserName is null
     */
    public SubprogramChair(String newUserName) {
        if(newUserName == null || newUserName.equals("")) {
            throw new NullPointerException("Invalid Username");
        }
        userName = newUserName;
    }

    /**
     * Assigns a reviewer to a Manuscript. No assignment if Manuscript is not assigned to Subprogram Chair
     * @param paper Manuscript that needs a reviewer
     * @param reviewer Reviewer to review the Manuscript
     * @exception NullPointerException if Manuscript does not exist (null)
     * @exception NullPointerException if Reviewer does not exist (null)
     */
    public void assignReviewer(Manuscript paper, Reviewer reviewer) {
        if(paper == null) {
            throw new NullPointerException();
        }
        if(reviewer == null) {
            throw new NullPointerException();
        }
        if(!paper.getSPCsUsername().equals(userName)) {
            //Incorrect SPC for paper.
            return;
        }
        paper.addReviewer(reviewer);
    }

    /**
     * Submits recommendation of Subprogram Chair
     * @param theRecommendation String that contains the Subprogram Chair's recommendation
     */
    public void submitRecommendation(Manuscript theManuscript, Recommendation theRecommendation) {
        
        theManuscript.setRecommendation(theRecommendation);
    }

    /**
     * Getter. Gets username of Subprogram Chair
     * @return Returns username
     */
    public String getUserName() {
        return userName;
    }
    
}
