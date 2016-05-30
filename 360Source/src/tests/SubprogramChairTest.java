/*****************************************************
 * Group 3: Lisa Taylor, Nathanael Toporek, Anh Tran *
 * TCSS 360, Spring 2016                             *
 * Deliverable #3                                    *
 *****************************************************/

package tests;
import org.junit.Before;
import org.junit.Test;

import enums.Recommendation;
import model.Manuscript;
import model.Reviewer;
import model.SubprogramChair;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author Anh Tran
 * @version 05/07/2016
 */
public class SubprogramChairTest {

    private static final String SPCUSERNAME = "SPC1";
    private static final String REVUSERNAME = "rev1";
    private static final String TITLE = "This is a test";
    private static final String AUTHOR = "name";
    private static final String USERNAME = "user1";
    private static final String PATH = "test/viruses/";
    private static final String RECOMMENDATION = "myRec";
    private SubprogramChair spc;
    private Reviewer rev1;
    private Manuscript paper1;
    private Manuscript paper2;

    @Before
    public void setup() throws Exception {
        spc = new SubprogramChair(SPCUSERNAME);
        rev1 = new Reviewer(REVUSERNAME);
        paper1 = new Manuscript(TITLE, AUTHOR, USERNAME, PATH);
        paper2 = new Manuscript(TITLE + "2", AUTHOR + "2", USERNAME + "2", PATH + "2/");
    }

    @Test(expected=NullPointerException.class)
    public void testSubprogramChair() throws Exception {
        SubprogramChair sub = new SubprogramChair(null);
    }

    @Test(expected=NullPointerException.class)
    public void testAssignReviewer() throws Exception {
        spc.assignReviewer(null, rev1);
    }

    @Test(expected=NullPointerException.class)
    public void testAssignReviewer2() throws Exception {
        spc.assignReviewer(paper1, null);
    }

    @Test(expected=NullPointerException.class)
    public void testAssignReviewer3() throws Exception {
        spc.assignReviewer(null, null);
    }

    //Will always fail. SubmitRecommendation class makes a new object. Cannot copy another object without it being different.
    @Test
    public void testSubmitRecommendation() throws Exception {
        Recommendation rec = Recommendation.STRONG_ACCEPT;
        Manuscript man = new Manuscript("Hi","Hi","Hi","/Hi");
        man.setSPCsUsername(spc.getUserName());
        spc.submitRecommendation(man, rec);
        assertEquals(rec, man.getRecommendation());
    }

    @Test(expected=NullPointerException.class)
    public void testSubmitRecommendation2() throws Exception {
        spc.submitRecommendation(null, null);
    }

    @Test
    public void testGetUserName() throws Exception {
        assertEquals("SPC1", spc.getUserName());
    }

}