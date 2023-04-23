package users;

import config.RestAssuredManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetUserTest {

    @BeforeAll
    static void setUp() {
        RestAssuredManager.configure("users");
    }

//    endpoint: /api/users/{id}
//    method: GET
//    description: Get user by id
//    status code: 200
//    response: User
//    {
//    "data": {
//        "id": 2,
//        "email": "janet.weaver@reqres.in",
//        "first_name": "Janet",
//        "last_name": "Weaver",
//        "avatar": "https://reqres.in/img/faces/2-image.jpg"
//    },
//    "support": {
//        "url": "https://reqres.in/#support-heading",
//        "text": "To keep ReqRes free, contributions towards server costs are appreciated!"
//    }
//}


    @Test
    public void getUserTest() {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        String urlRegex = "^(https)://[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";

        given()
                .basePath("/api/users/{id}")
                .pathParam("id", 2)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .body("data.email", matchesPattern(emailRegex))
                .body("data.first_name", equalTo("Janet"))
                .body("data.last_name", equalTo("Weaver"))
                .body("data.avatar", matchesPattern(urlRegex))
                .body("support.url", matchesPattern(urlRegex))
                .body("support.text", containsString("ReqRes free"));
    }

    @Test
    public void getUserNotFoundTest() {
        given()
                .basePath("/api/users/{id}")
                .pathParam("id", 100)
                .when()
                .get()
                .then()
                .statusCode(404);
    }
}
