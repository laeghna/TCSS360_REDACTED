/*****************************************************
 * Group 3: Lisa Taylor, Nathanael Toporek, Anh Tran *
 * TCSS 360, Spring 2016                             *
 * Deliverable #3                                    *
 *****************************************************/

package tests;

import org.junit.Before;
import org.junit.Test;

import model.Review;
import model.Reviewer;

import static org.junit.Assert.*;

/**
 * @author Anh Tran
 * @version 05/30/2016
 */
public class ReviewTest {

    private static final String USERNAME = "user1";
    private static final String BLANK = "";
    private static final String REVIEW = "myReview";
    private Reviewer rev1;
    private Review review1;

    @Before
    public void setup() throws Exception {
        rev1 = new Reviewer(USERNAME);
        review1 = new Review(rev1, REVIEW);
    }

    /**
     * Tests creation of a Review with an invalid (null) Reviewer 
     */
    @Test(expected=NullPointerException.class)
    public void testReviewWithInvalidReviewer() throws Exception {
        Review review = new Review(null);
    }

    /**
     * Tests creation of a Review with a valid Reviewer, but invalid review data 
     */
    @Test(expected=NullPointerException.class)
    public void testReviewWithValidReviewerButInvalidData() throws Exception {
        Review review = new Review(rev1, null);
    }

    /**
     * Tests creation of a Review with an invalid Reviewer and invalid review data 
     */
    @Test(expected=NullPointerException.class)
    public void testReviewWithNoReviewerAndNoReviewData() throws Exception {
        Review review = new Review(null, null);
    }

    @Test
    public void testGetReviewer() throws Exception {
        assertEquals(rev1, review1.getReviewer());
    }

    @Test
    public void testGetReviewSubmission() throws Exception {
        assertEquals("myReview", review1.getReviewSubmission());
    }

    @Test(expected=NullPointerException.class)
    public void testSetReviewerWithInvalidData() throws Exception {
        review1.setReviewer(null);
    }

    @Test(expected=NullPointerException.class)
    public void testSetReviewSubmissionWithInvalidData() throws Exception {
        review1.setReviewSubmission(null);
    }

}
