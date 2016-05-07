package model;

import java.util.ArrayList;

/** 
 * Class that provides the UI menus for a Program Chair. 
 * 
 * @author Lisa Taylor
 * @version 6 May 2016
 */
public class ProgramChair {

    /** The list of Subprogram Chairs. */
    private ArrayList<SubprogramChair> mySPCs;
    
    /** Constructs a ProgramChair object. 
     * 
     * @param theSPCs the Subprogram Chairs
     */
    public ProgramChair(ArrayList<SubprogramChair> theSPCs) {
        mySPCs = theSPCs;
    }
    
    /** Adds a designated registered user to the list of Subprogram Chairs. 
     * 
     * @param the registered user
     */
    public void addSPC(RegisteredUser user) {
        mySPCs.add(user);
    }
    
    /** Returns a list of Subprogram Chairs. */
    public ArrayList<SubprogramChair> getSPCS() {
        return mySPCS;
    }
    
    /** Assigns a Manuscript to a Subprogram Chair. 
     * 
     * @param index the index of the SPC the Manuscript is being assigned to
     * @param paper the Manuscript to be assigned
     */
    public void assignManuscriptToSPC(int index, Manuscript paper) {
        mySPCs[index].addManuscript(paper);
    }
    
    /** Accepts or rejects a Manuscript. 
     * 
     * @param paper the Manuscript being accepted or rejected
     * @param decision the acceptance or rejection of the Manuscript
     * @return the updated Manuscript
     */
    public void acceptOrRejectManuscript(final Manuscript paper, final String decision) {
        paper.setAcceptance(decision);
        return paper;
    }
}
