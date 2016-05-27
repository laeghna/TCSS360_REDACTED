package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AuthorTests.class,
				ConferenceTest.class, 
				ManuscriptTests.class, 
				ReviewerTest.class, 
				ReviewTest.class,
				SubprogramChairTest.class })
public class AllTests {

}
