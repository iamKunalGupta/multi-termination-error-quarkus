package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class MultiResourceTest {

    @Test
    public void shouldPass1() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(List.of("1"), ObjectMapperType.JACKSON_2)
                .post("/test/multi")
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldPass2() {
        given()
                .when()
                .contentType(ContentType.JSON)

                .body(List.of("1", "2"), ObjectMapperType.JACKSON_2)
                .post("/test/multi")
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldFail1() {
        given()
                .when()
                .contentType(ContentType.JSON)

                .body(List.of("1", "3"), ObjectMapperType.JACKSON_2)
                .post("/test/multi")
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldFail2() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(List.of("3"), ObjectMapperType.JACKSON_2)
                .post("/test/multi")
                .then()
                .statusCode(200);
    }

}