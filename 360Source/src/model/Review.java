package model;

import java.io.Serializable;

/**
 * Review class. Stores Reviewer and their review of a Manuscript
 * @author Anh Tran
 * @version 05/06/2016
 */
public class Review implements Serializable{

    //For Serialization only
    private static final long serialVersionUID = 8767635707411068900L;
    /**
     * Reviewer assigned to manuscript
     */
    private Reviewer reviewer;
    /**
     * Reviewer submission (if any)
     */
    private String reviewSubmission;

    /**
     * Default Constructor. No reviewer or review assigned. All fields to null.
     */
    public Review() {
        reviewSubmission = null;
        reviewer = null;
    }

    /**
     * Constructor. Assigns reviewer only.
     * @param newReviewer reviewer assigned to manuscript
     */
    public Review(Reviewer newReviewer) {
        if(newReviewer == null) {
            throw new NullPointerException("No such reviewer");
        }
        setReviewer(newReviewer);
        reviewSubmission = null;
    }

    /**
     * Full constructor. Assigns both a reviewer and their review.
     * @param newReviewer reviewer assigned to manuscript
     * @param review review submitted by reviewer
     */
    public Review(Reviewer newReviewer, String review) {
        if(newReviewer == null) {
            throw new NullPointerException("No such reviewer");
        }
        if(review == null) {
            throw new NullPointerException("No review found.");
        }
        setReviewer(newReviewer);
        setReviewSubmission(review);
    }

    /**
     * Getter. Returns reviewer.
     * @return reviewer that was assigned to the manuscript
     */
    public Reviewer getReviewer() {
        return reviewer;
    }

    /**
     * Getter. Returns submission.
     * @return Returns submission of reviewer. Null if none exists.
     */
    public String getReviewSubmission() {
        return reviewSubmission;
    }

    /**
     * Setter. Sets reviewer to manuscript
     * @param newReviewer
     * @exception NullPointerException if reviewer does not exist (null)
     */
    public void setReviewer(Reviewer newReviewer) {
        if(newReviewer == null) {
            throw new NullPointerException("No such reviewer");
        }
        reviewer = newReviewer;
    }

    /**
     * Setter. Sets new review submission
     * @param review review that is to be added
     * @exception IllegalArgumentException if no reviewer is assigned
     * @exception NullPointerException if review does not exist or not found
     */
    public void setReviewSubmission(String review) {
        if(reviewer == null) {
            throw new IllegalArgumentException("No reviewer assigned. Cannot add submission");
        }
        if(review == null) {
            throw new NullPointerException("No review found.");
        }
        reviewSubmission = review;
    }
}
