package model;
import java.io.Serializable;

/**
 * @author Anh Tran
 * @version 05052016 //Date Written
 */
public class SubprogramChair implements Serializable{

    //For Serialization only
    private static final long serialVersionUID = 3523359274963269247L;
    /**
     * Username of Subprogram Chair
     */
    String userName;

    /**
     * Constructor. Creates a subprogram chair role.
     * @param newUserName Username of Subprogram Chair
     */
    public SubprogramChair(String newUserName) {
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
     * @param recommendation String that contains the Subprogram Chair's recommendation
     * @return Object of the Recommendation class to be handled by main
     * @exception  NullPointerException if recommendation is null
     */
    public Recommendation submitRecommendation(String recommendation) {
        if(recommendation == null) {
            throw new NullPointerException("No recommendation found");
        }
        return new Recommendation(this, recommendation);
    }

    /**
     * Getter. Gets username of Subprogram Chair
     * @return Returns username
     */
    public String getUserName() {
        return userName;
    }
}
