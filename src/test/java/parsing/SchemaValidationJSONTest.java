package parsing;

import annotations.ParsingTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;

public class SchemaValidationJSONTest {
    private static final Path RESOURCES = Path.of("src", "test", "resources", "bookshop.json");
    private static String json;

    @BeforeAll
    static void init() throws IOException {
        json = Files.readString(RESOURCES);
    }

    @ParsingTest
    void jsonValidationUsingJsonSchema() {
        assertThat(json, matchesJsonSchemaInClasspath("bookstore-schema.json"));
    }

    @AfterAll
    static void teardown() {
        json = null;
    }
}
