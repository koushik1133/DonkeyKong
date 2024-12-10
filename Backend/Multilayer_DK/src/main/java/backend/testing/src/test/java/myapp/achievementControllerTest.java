package backend.testing.src.test.java.myapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort; // For Spring Boot 3
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class achievementControllerTest {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void testCreateAchievement() throws JSONException {
        // Prepare the JSON payload
        JSONObject achievement = new JSONObject();
        achievement.put("title", "New Achievement");
        achievement.put("description", "This is a new achievement.");

        // Send POST request to create a new achievement
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(achievement.toString())
                .when()
                .post("/Achievements");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for success message
        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);
    }

    @Test
    public void testGetAllAchievements() throws JSONException {
        // Send GET request to retrieve all achievements
        Response response = RestAssured.given()
                .when()
                .get("/Achievements");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Parse response body
        JSONArray achievementsArray = new JSONArray(response.getBody().asString());
        // Check that at least one achievement is returned
        assertTrue(achievementsArray.length() > 0);
    }

    @Test
    public void testUpdateAchievement() throws JSONException {
        // First, create a new achievement to update
        JSONObject achievement = new JSONObject();
        achievement.put("title", "Achievement to Update");
        achievement.put("description", "Original description.");

        Response postResponse = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(achievement.toString())
                .when()
                .post("/Achievements");

        assertEquals(200, postResponse.getStatusCode());

        // Retrieve the ID of the created achievement
        JSONArray achievementsArray = new JSONArray(RestAssured.given()
                .when()
                .get("/Achievements")
                .getBody()
                .asString());
        JSONObject lastAchievement = achievementsArray.getJSONObject(achievementsArray.length() - 1);
        Long achievementId = lastAchievement.getLong("id");

        // Prepare updated data
        JSONObject updatedAchievement = new JSONObject();
        updatedAchievement.put("title", "Updated Achievement Title");
        updatedAchievement.put("description", "Updated description.");

        // Send PUT request to update the achievement
        Response putResponse = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(updatedAchievement.toString())
                .when()
                .put("/Achievements/" + achievementId);

        // Check status code
        assertEquals(200, putResponse.getStatusCode());

        // Verify the update
        Response getResponse = RestAssured.given()
                .when()
                .get("/Achievements/" + achievementId);

        JSONObject updatedAchievementResponse = new JSONObject(getResponse.getBody().asString());
        assertEquals("Updated Achievement Title", updatedAchievementResponse.getString("title"));
        assertEquals("Updated description.", updatedAchievementResponse.getString("description"));
    }

    @Test
    public void testDeleteAchievement() throws JSONException {
        // First, create a new achievement to delete
        JSONObject achievement = new JSONObject();
        achievement.put("title", "Achievement to Delete");
        achievement.put("description", "This achievement will be deleted.");

        Response postResponse = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(achievement.toString())
                .when()
                .post("/Achievements");

        assertEquals(200, postResponse.getStatusCode());

        // Retrieve the ID of the created achievement
        JSONArray achievementsArray = new JSONArray(RestAssured.given()
                .when()
                .get("/Achievements")
                .getBody()
                .asString());
        JSONObject lastAchievement = achievementsArray.getJSONObject(achievementsArray.length() - 1);
        Long achievementId = lastAchievement.getLong("id");

        // Send DELETE request to delete the achievement
        Response deleteResponse = RestAssured.given()
                .when()
                .delete("/Achievements/" + achievementId);

        // Check status code
        assertEquals(200, deleteResponse.getStatusCode());

        // Verify the achievement is deleted
        Response getResponse = RestAssured.given()
                .when()
                .get("/Achievements/" + achievementId);

        // Should return 404 Not Found
        int statusCode = getResponse.getStatusCode();
        assertEquals(404, statusCode);
    }
}