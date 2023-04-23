package petstore.user;

import config.RestAssuredManager;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import petstore.user.dto.UserResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginTest {

    //    endpoint: /user/login
//    method: GET
//    description: Logs user into the system
//    status code: 200
//    response: User
//    response schema: User
//    query parameters: username, password
//    response body:
//{
//    "code": 200,
//        "type": "unknown",
//        "message": "logged in user session:1682070070016"
//}
    @Test
    public void loginTest() {
        RestAssuredManager.configure("petstore");

        UserResponse userResponse = given()
                .basePath("/user/login")
                .queryParam("username", "user")
                .queryParam("password", "secret")
                .get()
                .then()
                .extract()
                .response()
                .body()
                .as(UserResponse.class);

        assertThat(userResponse.getCode(), Matchers.is(200));
        assertThat(userResponse.getType(), Matchers.is("unknown"));
        assertThat(userResponse.getMessage(), Matchers.containsString("logged in user session:"));
    }

    @Test
    @Disabled
    public void loginWithInvalidCredentialsTest() {
        String username = "notUser";
        String password = "notSecret";


        given()
                .basePath("/user/login")
                .queryParam("username", username)
                .queryParam("password", password)
                .when()
                .get()
                .then()
                .statusCode(400);
    }

    @Test
    public void logoutTest() {
        given()
                .basePath("/user/logout")
                .when()
                .get()
                .then()
                .statusCode(200);
    }


}
