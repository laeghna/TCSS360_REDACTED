package tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Anh Tran
 * @version 05/07/2016
 */
public class RecommendationTest {

    private static final String RECOMMENDATION = "myRec";
    private SubprogramChair spc;
    private Recommendation rec;

    @Before
    public void setup() throws Exception {
        spc = new SubprogramChair("SPC1");
        rec = new Recommendation(spc, RECOMMENDATION);
    }

    @Test(expected=NullPointerException.class)
    public void testRecommendation() throws Exception {
        Recommendation recTest = new Recommendation(null, RECOMMENDATION);
    }

    @Test
    public void testGetSPC() throws Exception {
        assertEquals(spc, rec.getSPC());
    }

    @Test
    public void testGetRecommendation() throws Exception {
        assertEquals("myRec", rec.getRecommendation());
    }

    @Test(expected=NullPointerException.class)
    public void testSetSPC() throws Exception {
        rec.setSPC(null);
    }

    @Test(expected=NullPointerException.class)
    public void testSetRecommendation() throws Exception {
        rec.setRecommendation(null);
    }

}