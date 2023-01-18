package com.raquel.hexagon;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class PizzaApiResourceITTest {
    @Inject
    ArrayListPizzeriaRepository arrayListPizzeriaRepository;

    @BeforeEach
    void setUp() {
        arrayListPizzeriaRepository.storePizza(new Pizza("pepperoni", 15));
        arrayListPizzeriaRepository.storePizza(new Pizza("barbeque", 19));
    }

    @BeforeEach
    void tearDown() {
        arrayListPizzeriaRepository.resetSystem();
    }

    @Test
    void shouldReturnEmptyListWhenCallingGetAllPizzas() {
        arrayListPizzeriaRepository.resetSystem();

        given().contentType(ContentType.JSON)
                .when().get("/pizzas")
                .then()
                .statusCode(200)
                .body("$.size()", is(0));
    }

    @Test
    public void shouldReturn2ItemListWhenGettingAllPizzas() {
        given().contentType(ContentType.JSON)
                .when().get("/pizzas")
                .then()
                .statusCode(200)
                .body("$.size()", is(2));
    }

    @Test
    public void shouldReturnPizzaWhenGettingExistingPizza() {
        given().contentType(ContentType.JSON)
          .when().get("/pizzas/pepperoni")
          .then()
             .statusCode(200)
             .body("name", is("pepperoni"))
             .body("price", is(15));
    }

    @Test
    public void shouldReturn404WhenGettingNotExistingPizza() {
        given().contentType(ContentType.JSON)
                .when().get("/pizzas/carbonara")
                .then()
                .statusCode(404);
    }

    @Test
    public void shouldUpdatePizzaPrice(){
        String pizzaName = "pepperoni";
        int pizzaPrice = 20;

        given().contentType(ContentType.JSON)
                .when().post("/pizzas/" + pizzaName + "/" + pizzaPrice)
                .then()
                .statusCode(200)
                .body("name", is(pizzaName))
                .body("price", is(pizzaPrice));
    }

    @Test
    public void shouldReturn404WhenUpdatingNotExistingPizzaPrice() {
        String pizzaName = "carbonara";
        int pizzaPrice = 20;

        given()
                .contentType(ContentType.JSON)
                .when().post("/pizzas/" + pizzaName + "/" + pizzaPrice)
                .then()
                .statusCode(404);
    }

}