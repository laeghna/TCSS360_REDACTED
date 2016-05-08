package model;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Anh Tran
 * @version 05/07/2016
 */
public class SubprogramChair implements Serializable{

    //For Serialization only
    private static final long serialVersionUID = 3523359274963269247L;
    /**
     * Max amount of manuscripts assigned to Subprogram Chair
     */
    private static final int MAXPAPERS = 4;
    /**
     * Username of Subprogram Chair
     */
    private String userName;

    /**
     * List of assigned manuscripts to Subprogram Chair
     */
    private ArrayList<Manuscript> assignedManuscripts;

    /**
     * Constructor. Creates a subprogram chair role.
     * @param newUserName Username of Subprogram Chair
     * @exception NullPointerException if newUserName is null
     */
    public SubprogramChair(String newUserName) {
        if(newUserName == null || newUserName == "") {
            throw new NullPointerException("Invalid Username");
        }
        assignedManuscripts = new ArrayList<Manuscript>();
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

    /**
     * Getter. Gets list of Manuscripts belonging to Subprogram Chair
     * @return  Subprogram Chair's list of Manuscripts
     */
    public ArrayList<Manuscript> getAssignedManuscripts() {
        return new ArrayList<Manuscript>(assignedManuscripts);
    }

    /**
     * Adds Manuscript to Subprogram Chair. Max size is 4.
     * @param newManuscript Manuscript to be added to Subprogram Chair
     * @exception  NullPointerException if Manuscript does not exist (null)
     */
    public void addManuscript(Manuscript newManuscript) {
        if(newManuscript == null) {
            throw new NullPointerException("No manuscript found");
        }
        if(assignedManuscripts.size() == MAXPAPERS) {
            System.out.println("Cannot assign any more manuscripts");
        }
        else if(assignedManuscripts.contains(newManuscript)) {
            System.out.println("Manuscript already added!");
        }
        else
            assignedManuscripts.add(newManuscript);
    }

    /**
     * Removes Manuscript from Subprogram Chair List.
     * @param toRemove Manuscript to be removed from Subprogram Chair
     * @exception  NullPointerException if Manuscript does not exist (null)
     */
    public void removeManuscript(Manuscript toRemove) {
        if(toRemove == null) {
            throw new NullPointerException("No manuscript found");
        }
        if(assignedManuscripts.isEmpty()) {
            System.out.println("List is empty. Nothing to remove");
        }
        if(!assignedManuscripts.contains(toRemove)) {
            System.out.println("Manuscript not assigned. No removal");
        }
        else
            assignedManuscripts.remove(toRemove);
    }
}
