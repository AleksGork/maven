import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CalculatorTest {

    @BeforeMethod
    public void setUp() {
        System.out.println("Начало теста");
    }

    @Test
    public void testAddition() {
        int result = 2 + 2;
        Assert.assertEquals(result, 4);
    }

    @AfterMethod
    public void tearDown() {
        System.out.println("Окончание теста");
    }
}
