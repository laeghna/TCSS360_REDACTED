/*****************************************************
 * Group 3: Lisa Taylor, Nathanael Toporek, Anh Tran *
 * TCSS 360, Spring 2016                             *
 * Deliverable #3                                    *
 *****************************************************/

package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Author;
import model.Manuscript;
import model.Recommendation;
import model.Review;
import model.Reviewer;
import model.SubprogramChair;
/**
 * A class to contain tests for the Manuscript class.
 * @author Nathanael Toporek, nat96@uw.edu
 *
 */
public class ManuscriptTests {

	private static final String JohnsName = "John Smith";
	private static final String JanesName = "Jane Smith";
	private static final String JohnsUsername = "JohnSmith";
	private static final String JanesUsername = "JaneSmith";
	
	private Author JohnSmith;
	private Author JaneSmith;
	
	private Manuscript JohnsDoc;
	private Manuscript JanesDoc;
	
	private Reviewer JaneAsReviewer;
	private Review JanesReview;
	
	@Before
	public void setupDocs() {
		
		JohnSmith = new Author(JohnsUsername);
		JaneSmith = new Author(JanesUsername);
		
		JohnsDoc = new Manuscript("Potatoes", JohnsName, JohnsUsername, "/");
		JanesDoc = new Manuscript("Potats", JanesName, JanesUsername, "/potato");
		
		JaneAsReviewer = new Reviewer(JanesUsername);
		JanesReview = new Review(JaneAsReviewer);
	}
	
	@Test(expected = SecurityException.class)
	public void testRecommendationBeforeSPCisAssigned() {

		Recommendation rec = new Recommendation(new SubprogramChair("pot"), "ato");
		
		JohnsDoc.setRecommendation(rec);
	}
	
	@Test(expected = SecurityException.class)
	public void testAddingReviewerBeforeSPC() {
		
		Reviewer rev1 = new Reviewer("ohai");
		
		JohnsDoc.addReviewer(rev1);
	}
	
	@Test(expected = NullPointerException.class)
	public void testAddingNullReviewer() {
		
		JohnsDoc.addReviewer(null);
	}
	
	@Test(expected = SecurityException.class) 
	public void testAddingAuthorAsAReviewer() {
		
		Reviewer rev1 = new Reviewer(JohnsUsername);
		
		JohnsDoc.addReviewer(rev1);
	}
	
	@Test(expected = SecurityException.class)
	public void testAddingAuthorAsSubprogramChair() {
		
		JohnsDoc.setSPCsUsername(JohnsUsername);
	}
	
	@Test(expected = SecurityException.class) 
	public void testAddingReviewWhenReviewerCantSubmitAReview() {
		
		JohnsDoc.addNewReview(JaneAsReviewer, JanesReview);		
	}
	
	@Test(expected = SecurityException.class)
	public void testAddingAMisMatchedReveiw() {
		
		JohnsDoc.addNewReview(JaneAsReviewer, JanesReview);
	}
	
	@Test(expected = SecurityException.class) 
	public void testRemovingReviewWhenNotAllowed() {
				
		JohnsDoc.removeReview(new Reviewer("potato"));
	}
	
	@Test(expected = SecurityException.class)
	public void testChangineReviewWhenNotAllowed() {
		
		JohnsDoc.changeReview(new Reviewer("Potato"), JanesReview);
	}
}
