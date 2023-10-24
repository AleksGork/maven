import pages.AuchanStorePage;
import pages.MetroStorePage;
import pages.SbermarketMainPage;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertNotEquals;
import org.testng.annotations.BeforeSuite;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import io.qameta.allure.Step;

public class SbermarketTest {

    @BeforeSuite
    public void setUp() {
        System.setProperty("selenide.browser", "safari");
        SelenideLogger.addListener("allure", new AllureSelenide().screenshots(true).savePageSource(true));
    }

    @Test
    public void compareProductPrices() {
        SbermarketMainPage mainPage = openSbermarketMainPage();

        double metroPrice = getPriceFromMetro(mainPage);
        double auchanPrice = getPriceFromAuchan(mainPage);

        assertNotEquals(metroPrice, auchanPrice, "Prices from METRO and AUCHAN are the same!");
    }

    @Step("Open Sbermarket Main Page")
    private SbermarketMainPage openSbermarketMainPage() {
        return open("https://sbermarket.ru/", SbermarketMainPage.class);
    }

    @Step("Get price of 'молоко агуша' from METRO")
    private double getPriceFromMetro(SbermarketMainPage mainPage) {
        MetroStorePage metroPage = mainPage.navigateToMetro();
        metroPage.searchForProduct("молоко агуша");
        double metroPrice = Double.parseDouble(metroPage.getFirstProductPrice().replaceAll("[^\\d.]", ""));
        back();
        return metroPrice;
    }

    @Step("Get price of 'молоко агуша' from AUCHAN")
    private double getPriceFromAuchan(SbermarketMainPage mainPage) {
        AuchanStorePage auchanPage = mainPage.navigateToAuchan();
        auchanPage.searchForProduct("молоко агуша");
        return Double.parseDouble(auchanPage.getFirstProductPrice().replaceAll("[^\\d.]", ""));
    }
}
