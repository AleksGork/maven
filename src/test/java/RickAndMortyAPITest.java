import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.qameta.allure.Step;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RickAndMortyAPITest {

    private static final String BASE_URL = "https://rickandmortyapi.com/api/";

    @Test(description = "Test Morty's last episode character")
    @Description("Description: Tests the last episode character of Morty using Rick and Morty API")
    public void testMortyLastEpisodeCharacter() {
        try {
            findMortySmith();
            String lastEpisodeUrl = getLastEpisodeUrl();
            String lastCharacterUrl = getLastCharacterUrlFromEpisode(lastEpisodeUrl);
            String lastCharacterLocation = getLastCharacterLocation(lastCharacterUrl);
            String mortyLocation = getMortyLocation();

            // Проверка
            Assert.assertEquals(lastCharacterLocation, mortyLocation, "The locations do not match!");

            // Сравнение расы последнего персонажа и Morty
            compareSpecies(lastCharacterUrl);

        } catch (Exception e) {
            System.out.println("An error occurred while processing the API request: " + e.getMessage());
        }
    }

    @Step("Find Morty Smith")
    private void findMortySmith() {
        Response response = given().baseUri(BASE_URL).get("character/?name=Morty Smith");
        JsonPath jsonPath = response.jsonPath();
        String mortyName = jsonPath.getString("results[0].name");
        System.out.println(mortyName);
    }

    @Step("Get last episode URL")
    private String getLastEpisodeUrl() {
        Response response = given().baseUri(BASE_URL).get("character/?name=Morty Smith");
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getString("results[0].episode[-1]");
    }

    @Step("Get last character URL from episode {0}")
    private String getLastCharacterUrlFromEpisode(String episodeUrl) {
        Response response = given().baseUri(episodeUrl).get();
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getString("characters[-1]");
    }

    @Step("Get location of last character from URL {0}")
    private String getLastCharacterLocation(String characterUrl) {
        Response response = given().baseUri(characterUrl).get();
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getString("location.url");
    }

    @Step("Get Morty's location")
    private String getMortyLocation() {
        Response response = given().baseUri(BASE_URL).get("character/2");
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getString("location.url");
    }

    @Step("Compare species of the last character from URL {0} with Morty")
    private void compareSpecies(String characterUrl) {
        String lastCharacterSpecies = given().when().get(characterUrl).then().extract().path("species");
        String mortySpecies = given().when().get("https://rickandmortyapi.com/api/character/2").then().extract().path("species");
        Assert.assertEquals(lastCharacterSpecies, mortySpecies, "The species do not match!");
    }
}
