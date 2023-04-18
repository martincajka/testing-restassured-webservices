package petstore;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import petstore.dto.Category;
import petstore.dto.PetPostBody;
import petstore.dto.PetPostResponse;
import petstore.dto.Tag;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;

public class Pet {
    private static final String BASE_URL = "https://petstore.swagger.io/v2/pet";

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void addPet() {
        int id = 1;

        PetPostBody pet = new PetPostBody(id,
                new Category(id, "dog"),
                "my doggie",
                Collections.singletonList("url"),
                Collections.singletonList(new Tag(id, "tag")),
                "available");

        PetPostResponse petPostResponse = RestAssured.given()
                .contentType("application/json")
                .body(pet)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .as(PetPostResponse.class);


        assertThat(petPostResponse.getId(), Matchers.is(id));
        assertThat(petPostResponse.getCategory().getId(), Matchers.is(id));
        assertThat(petPostResponse.getCategory().getName(), Matchers.is("dog"));
        assertThat(petPostResponse.getName(), Matchers.is("my doggie"));
        assertThat(petPostResponse.getPhotoUrls().get(0), Matchers.is("url"));
        assertThat(petPostResponse.getTags().get(0).getId(), Matchers.is(id));
        assertThat(petPostResponse.getTags().get(0).getName(), Matchers.is("tag"));
        assertThat(petPostResponse.getStatus(), Matchers.is("available"));
    }

    //    get not existing pet
    @Test
    public void getNotExistingPet() {
        int id = 0;

        RestAssured.given()
                .contentType("application/json")
                .when()
                .get("/" + id)
                .then()
                .statusCode(404);
    }

    @AfterEach
    void tearDown() {
        RestAssured.baseURI = null;
    }
}
