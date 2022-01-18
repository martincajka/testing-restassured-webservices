package simplehttprequests;


import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetPostsAndCommentsTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";
    }

    @Test
    void getAllPosts() {
        Response response =
                when().
                        get("/posts").
                then().
                        contentType(ContentType.JSON).
                        statusCode(200).
                extract().
                        response();

        List<Map<String, Object>> posts = response.as(new TypeRef<>() {});

        assertThat(posts.size(), Matchers.equalTo(100));
    }


    @AfterAll
    static void teardown() {
        RestAssured.reset();
    }
}
