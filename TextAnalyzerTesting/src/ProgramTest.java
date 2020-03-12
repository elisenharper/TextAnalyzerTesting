import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


// Puts together all the tests for the text analyzer program

@RunWith(Suite.class)
@SuiteClasses({ WordFrequencyTest.class, WordListTest.class })

public class ProgramTest {

}
