package users;

import config.RestAssuredManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class RegisterUser {

    @BeforeEach
    void setUp() {
        RestAssuredManager.configure("users");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/users/registration.csv", numLinesToSkip = 1)
    public void registerUser(String username, String email, String password) {
        HashMap<String, String> registrationReq = new HashMap<>();
        registrationReq.put("username", username);
        registrationReq.put("email", email);
        registrationReq.put("password", password);

        given()
                .log().all()
                .basePath("/api/register")
                .body(registrationReq)
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(200)
                .body("id", notNullValue())
                .body("token", notNullValue());

    }
}
