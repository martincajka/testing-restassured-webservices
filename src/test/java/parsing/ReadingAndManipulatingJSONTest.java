package parsing;

import annotations.ParsingTest;
import io.restassured.path.json.JsonPath;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import utils.DateValidator;
import utils.DateValidatorUsingDateTimeFormatter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReadingAndManipulatingJSONTest {
    private static final Path RESOURCES = Path.of("src", "test", "resources", "bookshop.json");
    private static JsonPath json;

    @BeforeAll
    static void init() throws IOException {
        json = JsonPath.from(Files.newBufferedReader(RESOURCES));
    }

    @ParsingTest
    void jsonIsNotNull() {
        assertThat(json, not(nullValue()));
    }

    //    accessing unnamed root element using "$" sign , empty quotes works fine as well ("")
    @ParsingTest
    void jsonContainsExpectedNumberOfObjects() {
        assertThat(json.getList("bookstore").size(), is(3));
    }

    @ParsingTest
    void allBooksHasPriceGreaterThanZero() {
        float minPrice = json.getFloat("bookstore.min {it.price}.price");

        assertThat(minPrice, Matchers.greaterThan(0.00f));
    }

    @ParsingTest
    void allBooksHasLessThan5000Pages() {
        int maxNumberOfPages = json.getInt("bookstore.max {it.numberOfPages}.numberOfPages");

        assertThat(maxNumberOfPages, Matchers.lessThanOrEqualTo(5000));
    }

    @ParsingTest
    void allBooksHasCorrectPublicationDateFormat() {
        List<String> publicationDates = json.getList("bookstore.publicationDate");
        DateValidator validator = new DateValidatorUsingDateTimeFormatter(DateTimeFormatter.ISO_LOCAL_DATE);

        assertTrue(publicationDates.stream().allMatch(validator::isValid));
    }

    @ParsingTest
    void allBooksContainsISBNTest() {
        List<String> publicationDates = json.get("bookstore.findAll {book -> book.isbn == null}");

        assertThat("Bookstore does not contain any book without ISBN", publicationDates, Matchers.hasSize(0));
    }

    @AfterAll
    static void teardown() {
        json = null;
    }

}
