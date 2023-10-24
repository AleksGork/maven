package api;

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
                .contentType("application/json");
    }

    @Test(priority = 1)
    public void createUserTest() {
        File jsonBody = new File("src/test/resources/user_body.json");
        Response response = request
                .body(jsonBody)
                .post("/users");

        assertEquals(response.getStatusCode(), 201);
        assertEquals(response.jsonPath().getString("name"), "Alex");

        userId = response.jsonPath().getString("id");
    }

    @Test(priority = 2)
    public void updateUserTest() {
        String updatedBody = "{ \"name\": \"Aleksandr\", \"job\": \"SberMarket\" }";
        Response response = request
                .body(updatedBody)
                .put("/users/" + userId);

        assertEquals(response.getStatusCode(), 200);
        assertEquals(response.jsonPath().getString("name"), "Aleksandr");
        assertEquals(response.jsonPath().getString("job"), "SberMarket");
    }
}
