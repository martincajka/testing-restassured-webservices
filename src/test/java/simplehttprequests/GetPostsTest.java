package simplehttprequests;


import com.google.common.collect.Ordering;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetPostsTest {
    private static ValidatableResponse data;

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";
        data = when().
                get("/posts").
                then().
                contentType(ContentType.JSON).
                statusCode(200);
    }

    @Test
    void getAllPosts() {
        List<Map<String, Object>> posts = data.extract().response().as(new TypeRef<>() {
        });

        assertThat(posts.size(), equalTo(100));
    }

    @Test
    void verifyThatPostsIdContainsOnlyPositiveIntegers() {
        data.body("id", everyItem(greaterThan(0)));
    }

    @Test
    void verifyThatPostsTitleAndBodyAreOfTypeString() {
        data.body("[0].title", instanceOf(String.class)).
                and().body("[0].body", instanceOf(String.class));
    }

    @Test
    void verifyThatAllPostsTitleHasSizeLessThan250() {
        data.body("title*.length()", everyItem(lessThan(250)));
    }

    @Test
    void verifyThatAllPostsBodiesHasSizeInBetween1to1000Inclusive() {
        data.body("body*.length().max()",
                        allOf(greaterThanOrEqualTo(1), lessThanOrEqualTo(1000)));
    }

    @AfterAll
    static void teardown() {
        RestAssured.reset();
        data = null;
    }
}
