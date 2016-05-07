package model;

import java.io.Serializable;

/**
 * Recommendation class. Stores Subprogram Chair and their recommendation.
 * @author Anh Tran
 * @version 05/06/2016
 */
public class Recommendation implements Serializable{

    //For Serialization only
    private static final long serialVersionUID = -6783961909189765303L;
    /**
     * Subprogram Chair that made recommendation
     */
    private SubprogramChair SPC;
    /**
     * Recommendation of Subprogram Chair
     */
    private String recommendation;

    /**
     * Constructor. Creates recommendation class
     * @param spc Subprogram Chair that makes recommendation
     * @param rec Recommendation of Subprogram Chair
     * @exception NullPointerException if spc (Subprogram Chair) is null
     */
    public Recommendation(SubprogramChair spc, String rec) {
        if(spc == null) {
            throw new NullPointerException("SPC does not exist");
        }
        SPC = spc;
        recommendation = rec;
    }

    /**
     * Getter. Gets Subprogram Chair
     * @return Returns the Subprogram Chair
     */
    public SubprogramChair getSPC() {
        return SPC;
    }

    /**
     * Getter. Gets the submitted recommendation
     * @return Returns the Subprogram Chair's recommendation
     */
    public String getRecommendation() {
        return recommendation;
    }

    /**
     * Setter. Sets the Subprogram Chair.
     * @param newSPC The Subprogram Chair that makes the recommendation
     * @exception NullPointerException if Subprogram Chair does not exist (null)
     */
    public void setSPC(SubprogramChair newSPC) {
        if(newSPC == null) {
            throw new NullPointerException("No Subprogram Chair found");
        }
        SPC = newSPC;
    }

    /**
     * Setter. Sets the recommendation
     * @param newRec The submitted recommendation
     * @exception NullPointerException if recommendation does not exist (null)
     */
    public void setRecommendation(String newRec) {
        if(newRec == null) {
            throw new NullPointerException("No recommendation found");
        }
        recommendation = newRec;
    }
}
