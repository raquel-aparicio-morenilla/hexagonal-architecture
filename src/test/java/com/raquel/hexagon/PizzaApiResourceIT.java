package com.raquel.hexagon;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class PizzaApiResourceIT {

    private final String PEPPERONI_PIZZA_NAME = "pepperoni";
    private final int PEPPERONI_PIZZA_PRICE = 15;
    private final String BARBEQUE_PIZZA_NAME = "barbeque";
    private final int BARBEQUE_PIZZA_PRICE = 19;

    @Inject
    PizzeriaRepository pizzeriaRepository;

    @BeforeEach
    void setUp() {
        pizzeriaRepository.storePizza(new Pizza(PEPPERONI_PIZZA_NAME, PEPPERONI_PIZZA_PRICE));
        pizzeriaRepository.storePizza(new Pizza(BARBEQUE_PIZZA_NAME,  BARBEQUE_PIZZA_PRICE));
    }

    @BeforeEach
    void tearDown() {
        pizzeriaRepository.resetSystem();
    }

    @Test
    void shouldReturnEmptyListWhenCallingGetAllPizzas() {
        pizzeriaRepository.resetSystem();

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
             .when().get("/pizzas/" + PEPPERONI_PIZZA_NAME)
             .then()
             .statusCode(200)
             .body("name", is(PEPPERONI_PIZZA_NAME))
             .body("price", is(PEPPERONI_PIZZA_PRICE));
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
        String pizzaName = PEPPERONI_PIZZA_NAME;
        int pizzaPrice = PEPPERONI_PIZZA_PRICE + 1;

        given().contentType(ContentType.JSON)
                .when().get("/pizzas/" + PEPPERONI_PIZZA_NAME)
                .then()
                .statusCode(200)
                .body("name", is(pizzaName))
                .body("price", is(PEPPERONI_PIZZA_PRICE));

        final JsonPizza jsonPizza = JsonPizza.builder()
                .name(pizzaName)
                .price(pizzaPrice)
                .build();

        given().contentType(ContentType.JSON)
                .body(jsonPizza)
                .when()
                .post("/pizzas")
                .then()
                .statusCode(200)
                .body("name", is(pizzaName))
                .body("price", is(pizzaPrice));
    }

    @Test
    public void shouldReturn404WhenUpdatingNotExistingPizzaPrice() {
        String pizzaName = "carbonara";
        int pizzaPrice = 20;

        final JsonPizza jsonPizza = JsonPizza.builder()
                .name(pizzaName)
                .price(pizzaPrice)
                .build();

        given().contentType(ContentType.JSON)
                .body(jsonPizza)
                .when().post("/pizzas/")
                .then()
                .statusCode(404);
    }

}