package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import org.openqa.selenium.By;

public class AuchanStorePage {
    private SelenideElement searchInput = $(By.xpath("//*[@id='__next']/div[1]/header/div/div[3]/div/div/div[3]/div/div/div/form"));
    private ElementsCollection products = $$(By.xpath("//*[@id='__next']/div[1]/section[2]/div/div/main/div/div"));
    private SelenideElement firstProduct = products.get(0);
    private SelenideElement firstProductPrice = firstProduct.$(By.xpath("//*[@id='__next']/div[1]/section[2]/div/div/main/div/div[1]/div/div[1]/a/div[5]/div/text()"));

    public void searchForProduct(String productName) {
        searchInput.click();
        searchInput.sendKeys(productName);
        searchInput.pressEnter();
    }

    public String getFirstProductPrice() {
        return firstProductPrice.text();
    }
}
