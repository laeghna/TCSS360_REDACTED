package tests;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Anh Tran
 * @version 05/07/2016
 */
public class ReviewerTest {

    private static final String USERNAME = "user1";
    private static final String BLANK = "";
    private static final String REVIEW = "myReview";
    private Reviewer rev1;

    @Before
    public void setup() throws Exception {
        rev1 = new Reviewer(USERNAME);
    }

    @Test(expected=NullPointerException.class)
    public void testReview() throws Exception {
        Reviewer blankUser = new Reviewer(BLANK);
    }

    @Test(expected=NullPointerException.class)
    public void testReview2() throws Exception {
        Reviewer nullUser = new Reviewer(null);
    }

    @Test
    public void testSubmitReview() throws Exception {
        assertEquals("myReview", rev1.submitReview(REVIEW));
    }

    @Test(expected=NullPointerException.class)
    public void testSubmitReview2() throws Exception {
        assertEquals(null, rev1.submitReview(null));
    }

    @Test(expected=NullPointerException.class)
    public void testSubmitReview3() throws Exception {
        assertEquals(null, rev1.submitReview(BLANK));
    }

    //Do not really need this. Uses submitReview() as a subroutine.
    @Test
    public void testChangeReview() throws Exception {
        assertEquals("myReview", rev1.changeReview(REVIEW));
    }

    @Test
    public void testUnsubmitReview() throws Exception {
        assertEquals(null, rev1.unSubmitReview());
    }

    @Test
    public void testGetUsername() throws Exception {
        assertEquals("user1", rev1.getUsername());
    }

}