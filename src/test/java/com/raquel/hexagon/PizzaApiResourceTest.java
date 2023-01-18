package com.raquel.hexagon;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@QuarkusTest
class PizzaApiResourceTest {

    @InjectMock
    PizzeriaRepository pizzeriaRepository = mock(PizzeriaRepository.class);

    @Test
    void shouldReturnEmptyListWhenCallingGetAllPizzas() {
        when(pizzeriaRepository.getAllPizzas()).thenReturn(new ArrayList<>());

        given().contentType(ContentType.JSON)
                .when().get("/pizzas")
                .then()
                .statusCode(200)
                .body("$.size()", is(0));
    }

    @Test
    void shouldReturn2ItemListWhenCallingGetAllPizzas() {
        when(pizzeriaRepository.getAllPizzas()).thenReturn(Arrays.asList(new Pizza("pepperoni", 15), new Pizza("barbeque", 19)));

        given().contentType(ContentType.JSON)
                .when().get("/pizzas")
                .then()
                .statusCode(200)
                .body("$.size()", is(2));
    }

    @Test
    public void shouldReturnPizzaWhenGettingExistingPizza() {
        String pizzaName = "pepperoni";
        int pizzaPrice = 20;

        Optional<Pizza> expectedResult = Optional.of(new Pizza(pizzaName, pizzaPrice));
        when(pizzeriaRepository.getPizza(pizzaName)).thenReturn(expectedResult);

        given().contentType(ContentType.JSON)
                .when().post("/pizzas/" +  pizzaName + "/" + pizzaPrice)
                .then()
                .statusCode(200)
                .body("name", is(pizzaName))
                .body("price", is(pizzaPrice));
    }

    @Test
    public void shouldReturn404WhenGettingNotExistingPizza() {
        String pizzaName = "pepperoni";

        given().contentType(ContentType.JSON)
                .when().get("/pizzas/" +  pizzaName)
                .then()
                .statusCode(404);
    }

    @Test
    public void shouldUpdatePizzaPrice() {
        String pizzaName = "pepperoni";
        int pizzaPrice = 20;

        Optional<Pizza> expectedResult = Optional.of(new Pizza(pizzaName, pizzaPrice));
        when(pizzeriaRepository.getPizza(pizzaName)).thenReturn(expectedResult);

        given()
                .contentType(ContentType.JSON)
                .when().post("/pizzas/" + pizzaName + "/" + pizzaPrice)
                .then()
                .statusCode(200)
                .body("name", is(pizzaName))
                .body("price", is(pizzaPrice));
    }

    @Test
    public void shouldReturn404WhenUpdatingPizzaPrice() {
        String pizzaName = "pepperoni";
        int pizzaPrice = 20;

        Optional<Pizza> expectedResult = Optional.empty();
        when(pizzeriaRepository.getPizza(pizzaName)).thenReturn(expectedResult);

        given()
                .contentType(ContentType.JSON)
                .when().post("/pizzas/" + pizzaName + "/" + pizzaPrice)
                .then()
                .statusCode(404);
    }
}