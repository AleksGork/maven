package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import org.openqa.selenium.By;

public class SbermarketMainPage {

    // XPath для магазина METRO
    private SelenideElement metroLink = $(By.xpath("//*[@class='Retailer_root__YPxOz' and @href='/metro']")).waitUntil(Condition.visible, 10000);

    // XPath для магазина Auchan
    private SelenideElement auchanLink = $(By.xpath("//*[@class='Retailer_root__YPxOz' and @href='/auchan']")).waitUntil(Condition.visible, 10000);

    public MetroStorePage navigateToMetro() {
        metroLink.scrollTo().click();
        return page(MetroStorePage.class);
    }

    public AuchanStorePage navigateToAuchan() {
        auchanLink.scrollTo().click();
        return page(AuchanStorePage.class);
    }

}
