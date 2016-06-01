/*****************************************************
 * Group 3: Lisa Taylor, Nathanael Toporek, Anh Tran *
 * TCSS 360, Spring 2016                             *
 * Deliverable #3                                    *
 *****************************************************/

package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

import org.junit.Before;
import org.junit.Test;

import model.Conference;
import model.Manuscript;
import model.ProgramChair;
import model.SubprogramChair;

public class ConferenceTest {

	Conference submissionDeadlineUpcoming;
	Conference submissionDeadlinePassed;
	ProgramChair PC1;
	ProgramChair PC2;
		
	@Before
	public void initializeConferences() {
		
		Date passedDL = new Date(System.currentTimeMillis() + 1);
		Date upcomingDL = new Date(System.currentTimeMillis() + 1000000000L);
		
		submissionDeadlineUpcoming = new Conference("UrMum",
				new ProgramChair("Kek", new ArrayList<String>()), upcomingDL, upcomingDL, upcomingDL);
		submissionDeadlinePassed = new Conference("UrMum",
				new ProgramChair("Kek", new ArrayList<String>()), passedDL, passedDL, passedDL);
	}
	@Test (expected=IllegalArgumentException.class)
	public void testAddingManuscriptAfterDeadline() {
		Manuscript m = new Manuscript(" 0"," 0"," 0"," 0");
		
		Timer t = new Timer();
		// Necessary for the deadline to pass.
		for(int i = 0; i < 1000000; i++) {
			
			int j = i * 9001;
		}
		
		submissionDeadlinePassed.addManuscript(m);
	}
	
	@Test
	public void testAddingManuscript() {
		Manuscript m = new Manuscript(" 0"," 0"," 0"," 0");
		
		submissionDeadlineUpcoming.addManuscript(m);
		assertEquals(submissionDeadlineUpcoming.getManuscripts().size(), 1);
	}
}
