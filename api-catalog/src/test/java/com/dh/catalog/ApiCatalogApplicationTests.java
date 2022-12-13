package com.dh.catalog;


import io.restassured.http.ContentType;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
//@SpringBootTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ApiCatalogApplicationTests {

    @Test
    public void addMovie() throws JSONException {

        JSONObject newMovie = new JSONObject();

        newMovie.put("name", "PeliculaTest");
        newMovie.put("genre", "X");
        newMovie.put("urlStream", "www.netflix.com");

        given()
                .contentType(ContentType.JSON)
                .body(newMovie.toString())
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/movies/save")
                .then()
                .assertThat().statusCode(200)
                .body("name", equalTo("PeliculaTest"))
                .body("genre", equalTo("X"))
                .log().all();
    }

    @Test
    public void addSerie() throws JSONException {

        JSONObject newSerie = new JSONObject();

        newSerie.put("name", "SerieTest");
        newSerie.put("genre", "X");
        /*No agrego temporadas ni capitulos*/

        given()
                .contentType(ContentType.JSON)
                .body(newSerie.toString())
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/series")
                .then()
                .assertThat().statusCode(200)
                .log().all();
    }

    @Test
    public void getCatalogOnline() {

        get("http://localhost:8080/api/v1/catalog/x")
                .then()
                .assertThat()
                .statusCode(200).log().all();
    }

    @Test
    public void getCatalogOffline() {

        /*----> DELAY, METODO OFFLINE <----*/

        try {
            System.out.println("Delay de 10 segundos. " + LocalDateTime.now());
            Thread.sleep(10000);
            System.out.println("Fin del Delay. " + LocalDateTime.now());
            get("http://localhost:8080/api/v1/catalog/offline/x")
                    .then()
                    .assertThat()
                    .statusCode(200).log().all();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /*@Test
    void contextLoads() {
    }*/

}