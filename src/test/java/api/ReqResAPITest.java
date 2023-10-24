package api;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.Assert.assertEquals;

public class ReqResAPITest {

    private final String BASE_URL = "https://reqres.in/api";
    private RequestSpecification request;
    private String userId;

    @BeforeClass
    public void setup() {
        request = RestAssured.with()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .filter(new AllureRestAssured());  // Добавление фильтра для Allure
    }

    @Test(priority = 1)
    public void createUserTest() {
        File jsonBody = new File("src/test/resources/user_body.json");
        Response response = createUser(jsonBody);

        assertEquals(response.getStatusCode(), 201);
        assertEquals(response.jsonPath().getString("name"), "Alex");

        userId = response.jsonPath().getString("id");
    }

    @Step("Создание пользователя с предоставленным JSON")
    private Response createUser(File jsonBody) {
        return request
                .body(jsonBody)
                .post("/users");
    }

    @Test(priority = 2)
    public void updateUserTest() {
        String updatedBody = "{ \"name\": \"Aleksandr\", \"job\": \"SberMarket\" }";
        Response response = updateUser(updatedBody, userId);

        assertEquals(response.getStatusCode(), 200);
        assertEquals(response.jsonPath().getString("name"), "Aleksandr");
        assertEquals(response.jsonPath().getString("job"), "SberMarket");
    }

    @Step("Обновление пользователя с ID {1} с предоставленным JSON")
    private Response updateUser(String updatedBody, String userId) {
        return request
                .body(updatedBody)
                .put("/users/" + userId);
    }
}
