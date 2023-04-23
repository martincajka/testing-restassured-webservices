package users;

import config.RestAssuredManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class CreateUserTest {

    @BeforeAll
    static void setUp() {
        RestAssuredManager.configure("users");
    }

/*        endpoint: /api/users
    method: POST
    description: Create user
    status code: 201

    request body:
    {
    "name": "morpheus",
            "job": "leader"
    }

    response: User
    {
            "id": "240",
            "createdAt": "2020-11-25T15:51:50.000Z"
    }*/


    @Test
    public void createUserTest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("morpheus");
        userRequest.setJob("leader");

        given()
                .basePath("/api/users")
                .body(userRequest)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("createdAt", notNullValue());
    }
}
