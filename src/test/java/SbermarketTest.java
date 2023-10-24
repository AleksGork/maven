import pages.AuchanStorePage;
import pages.MetroStorePage;
import pages.SbermarketMainPage;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertNotEquals;
import org.testng.annotations.BeforeSuite;

public class SbermarketTest {

    @BeforeSuite
    public void setUp() {
        System.setProperty("selenide.browser", "safari");
    }

    @Test
    public void compareProductPrices() {
        SbermarketMainPage mainPage = open("https://sbermarket.ru/", SbermarketMainPage.class);

        MetroStorePage metroPage = mainPage.navigateToMetro();
        metroPage.searchForProduct("молоко агуша");
        double metroPrice = Double.parseDouble(metroPage.getFirstProductPrice().replaceAll("[^\\d.]", ""));

        back();

        AuchanStorePage auchanPage = mainPage.navigateToAuchan();
        auchanPage.searchForProduct("молоко агуша");
        double auchanPrice = Double.parseDouble(auchanPage.getFirstProductPrice().replaceAll("[^\\d.]", ""));

        assertNotEquals(metroPrice, auchanPrice, "Prices from METRO and AUCHAN are the same!");
    }
}
