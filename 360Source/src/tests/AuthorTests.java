/*****************************************************
 * Group 3: Lisa Taylor, Nathanael Toporek, Anh Tran *
 * TCSS 360, Spring 2016                             *
 * Deliverable #3                                    *
 *****************************************************/

package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import model.Author;
import model.Conference;
import model.Manuscript;
import model.ProgramChair;

/**
 * A class to hold tests for the author class.
 * @author Nathanael Toporek, nat96@gmail.com
 *
 */
public class AuthorTests {
	
	private static final String JohnsName = "John Smith";
	private static final String JanesName = "Jane Smith";
	private static final String JohnsUsername = "JohnSmith";
	private static final String JanesUsername = "JaneSmith";
	
	private Author JohnSmith;
	private Author JaneSmith;
	
	private Manuscript JohnsDoc;
	private Manuscript JanesDoc;
	
	private Conference Conf;
	
	/**
	 * An initializer method for the tests.
	 */
	@Before
	public void createAuthorsAndManuscripts() {
		
		JohnSmith = new Author(JohnsUsername);
		JaneSmith = new Author(JanesUsername);
		
		JohnsDoc = new Manuscript("Potatoes", JohnsName, JohnsUsername, "/");
		JanesDoc = new Manuscript("Potats", JanesName, JanesUsername, "/potato");
		
		Conference Conf = new Conference("Potats", new ProgramChair("Feg", new ArrayList<String>()),
										new Date(System.currentTimeMillis() + 1000L),
										new Date(System.currentTimeMillis() + 1000L),
										new Date(System.currentTimeMillis() + 1000L));
		
		Conf.addManuscript(JanesDoc);
		Conf.addManuscript(JohnsDoc);
	}
	
	/**
	 * Tests removing a null paper.
	 */
	@Test(expected=NullPointerException.class)
	public void testRemoveNull() {
		
		JohnSmith.removeManuscript(null, Conf);
	}
	/**
	 * Tests removing a paper not owned by the current author.
	 */
	@Test(expected=SecurityException.class)
	public void testJohnRemovesJanesPaper() {
		
		JohnSmith.removeManuscript(JanesDoc, Conf);
	}
	/**
	 * Tests editing a null paper.
	 */
	@Test(expected=NullPointerException.class)
	public void testEditNull() {
		
		JohnSmith.editManuscript(null, "/dev/null");
	}
	/**
	 * Tests editing a paper not owned by the calling author.
	 */
	@Test(expected=SecurityException.class) 
	public void testJohnEditsJanesPaper() {
		
		JohnSmith.editManuscript(JanesDoc, "/dev/null");
	}
	/**
	 * Tests removing a paper owned by the calling author.
	 * 
	 * CURRENTLY A BUG DUE TO THE SHITTY WAY JAVA PASSES REFERENCES BY VALUE.
	 * *Screams internally*
	 */
	@Test
	public void testJohnRemovesHisPaper() {
		
		JohnSmith.removeManuscript(JohnsDoc, Conf);

		ArrayList<Manuscript> m = Conf.getMyManuscripts(JohnsUsername);
		
		assertEquals(m.size(), 0);
	}
	/**
	 * Tests editing a paper owned by the calling author.
	 */
	@Test
	public void testJaneEditsHerPaper() {
		
		String testStr = "/dev/null";
		JaneSmith.editManuscript(JanesDoc, testStr);
		
		assertEquals(testStr, JanesDoc.getPathname());	
	}
}
