/* Nathanael Toporek
 * TCSS 325, Spring 2016
 */
package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Author;
import model.Manuscript;

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
	
	/**
	 * An initializer method for the tests.
	 */
	@Before
	public void createAuthorsAndManuscripts() {
		
		JohnSmith = new Author(JohnsUsername);
		JaneSmith = new Author(JanesUsername);
		
		JohnsDoc = new Manuscript("Potatoes", JohnsName, JohnsUsername, "/");
		JanesDoc = new Manuscript("Potats", JanesName, JanesUsername, "/potato");
	}
	
	/**
	 * Tests removing a null paper.
	 */
	@Test(expected=NullPointerException.class)
	public void testRemoveNull() {
		
		JohnSmith.removeManuscript(null);;
	}
	/**
	 * Tests removing a paper not owned by the current author.
	 */
	@Test(expected=SecurityException.class)
	public void testJohnRemovesJanesPaper() {
		
		JohnSmith.removeManuscript(JanesDoc);
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
		
		JohnSmith.removeManuscript(JohnsDoc);
		
		assertNull(JohnsDoc);
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
