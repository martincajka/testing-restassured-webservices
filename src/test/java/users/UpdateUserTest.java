package users;

import config.RestAssuredManager;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;

public class UpdateUserTest {

    private static final String BASE_PATH = "/api/users";

    //        regex for  ISO_INSTANT date time format 2020-11-25T15:51:50.000Z
    private static final String DATE_REGEX = "^[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]+:[0-9]+.[0-9]+Z$";

    @BeforeEach
    public void setUp() {
        RestAssuredManager.configure("users");
    }

    @Test
    public void updateUserUsingPATCH() {
        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("name", "John");
        jsonBody.put("job", "Doctor");

        Map<Object, Object> response = given()
                .basePath(BASE_PATH)
                .body(jsonBody)
                .pathParam("id", 2)
                .patch("/{id}")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getMap("$");

        assertThat(response.entrySet(), Matchers.hasSize(1));
        assertThat((String) response.get("updatedAt"), Matchers.matchesPattern(DATE_REGEX));
    }

    @Test
    public void updateUserUsingPUT() {
        given()
                .contentType(ContentType.JSON)
                .basePath(BASE_PATH)
                .pathParam("id", 2)
                .body("{\"name\": \"morpheus\",\"job\": \"zion resident\"}")
                .put("/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", Matchers.is("morpheus"))
                .body("job", Matchers.is("zion resident"))
                .body("updatedAt", Matchers.matchesPattern(DATE_REGEX));
    }
}
