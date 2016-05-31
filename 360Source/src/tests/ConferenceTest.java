/*****************************************************
 * Group 3: Lisa Taylor, Nathanael Toporek, Anh Tran *
 * TCSS 360, Spring 2016                             *
 * Deliverable #3                                    *
 *****************************************************/

package tests;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import model.Conference;

public class ConferenceTest {

	Conference submissionDeadlineUpcoming;
	Conference submissionDeadlinePassed;
	ProgramChair PC1;
	ProgramChair PC2;
		
	@Before
	public void initializeConferences() {
		
		Date passedDL = new Date(0L);
		Date upcomingDL = new Date(System.currentTimeMillis() + 1000000000L);
		
		submissionDeadlineUpcoming = new Conference("UrMum",
				new ProgramChair("Kek", new ArrayList<SubprogramChair>()), );
	}
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
