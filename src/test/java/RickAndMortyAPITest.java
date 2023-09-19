import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RickAndMortyAPITest {

    private static final String BASE_URL = "https://rickandmortyapi.com/api/";

    @Test
    public void testMortyLastEpisodeCharacter() {
        try {
            // Найдите Морти Смита
            Response response = given().baseUri(BASE_URL).get("character/?name=Morty Smith");
            JsonPath jsonPath = response.jsonPath();
            String lastEpisodeUrl = jsonPath.getString("results[0].episode[-1]");
            String mortyName = jsonPath.getString("results[0].name");
            System.out.println(mortyName + "'s last episode URL: " + lastEpisodeUrl);

            // Получите информацию о последнем эпизоде
            response = given().baseUri(lastEpisodeUrl).get();
            jsonPath = response.jsonPath();
            String lastCharacterUrl = jsonPath.getString("characters[-1]");
            System.out.println("Last character URL from the episode: " + lastCharacterUrl);

            // Получите информацию о последнем персонаже
            response = given().baseUri(lastCharacterUrl).get();
            jsonPath = response.jsonPath();
            String lastCharacterLocation = jsonPath.getString("location.url");
            String lastCharacterName = jsonPath.getString("name");
            System.out.println(lastCharacterName + "'s location: " + lastCharacterLocation);

            // Получите информацию о местоположении Морти
            response = given().baseUri(BASE_URL).get("character/2");
            jsonPath = response.jsonPath();
            String mortyLocation = jsonPath.getString("location.url");
            String mortyLocationName = jsonPath.getString("location.name");
            System.out.println(mortyName + "'s location: " + mortyLocationName);

            // Проверка
            Assert.assertEquals(lastCharacterLocation, mortyLocation, "The locations do not match!");

            // Получение расы последнего персонажа из последнего эпизода Morty
            String lastCharacterSpecies = given().when().get(lastCharacterUrl).then().extract().path("species");

            // Получение расы Morty
            String mortySpecies = given().when().get("https://rickandmortyapi.com/api/character/2").then().extract().path("species");

            // Сравнение расы последнего персонажа и Morty
            Assert.assertEquals(lastCharacterSpecies, mortySpecies, "The species do not match!");

        } catch (Exception e) {
            System.out.println("An error occurred while processing the API request: " + e.getMessage());
        }
    }
}
