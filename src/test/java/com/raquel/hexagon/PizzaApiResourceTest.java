package com.raquel.hexagon;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@QuarkusTest
class PizzaApiResourceTest {

    @InjectMock
    PizzaService pizzaService = mock(PizzaService.class);

    @Test
    void shouldReturnEmptyListWhenCallingGetAllPizzas() {
        when(pizzaService.getAllPizzas()).thenReturn(new ArrayList<>());

        given().contentType(ContentType.JSON)
                .when().get("/pizzas")
                .then()
                .statusCode(200)
                .body("$.size()", is(0));
    }

    @Test
    void shouldReturn2ItemListWhenCallingGetAllPizzas() {
        when(pizzaService.getAllPizzas()).thenReturn(Arrays.asList(new Pizza("pepperoni", 15), new Pizza("barbeque", 19)));

        given().contentType(ContentType.JSON)
                .when().get("/pizzas")
                .then()
                .statusCode(200)
                .body("$.size()", is(2));
    }

    @Test
    public void shouldReturnPizzaWhenGettingExistingPizza() throws PizzaNotFoundException {
        String pizzaName = "pepperoni";
        int pizzaPrice = 20;

        Pizza pizza = new Pizza(pizzaName, pizzaPrice);
        when(pizzaService.getPizza(pizzaName)).thenReturn(pizza);

        given().contentType(ContentType.JSON)
                .when().get("/pizzas/" +  pizzaName)
                .then()
                .statusCode(200)
                .body("name", is(pizzaName))
                .body("price", is(pizzaPrice));
    }

    @Test
    public void shouldReturn404WhenGettingNotExistingPizza() throws PizzaNotFoundException {
        String pizzaName = "pepperoni";

        when(pizzaService.getPizza(pizzaName)).thenThrow(new PizzaNotFoundException(pizzaName + " is not on our menu"));

        given().contentType(ContentType.JSON)
                .when().get("/pizzas/" +  pizzaName)
                .then()
                .statusCode(404);
    }

    @Test
    public void shouldUpdatePizzaPrice() throws PizzaNotFoundException {
        String pizzaName = "pepperoni";
        int pizzaPrice = 20;

        Pizza pizza = new Pizza(pizzaName, pizzaPrice);
        when(pizzaService.updatePizzaPrice(pizzaName, pizzaPrice)).thenReturn(pizza);

        final JsonPizza jsonPizza = JsonPizza.builder()
                .name(pizzaName)
                .price(pizzaPrice)
                .build();

        given().contentType(ContentType.JSON)
                .body(jsonPizza)
                .when().post("/pizzas/")
                .then()
                .statusCode(200)
                .body("name", is(pizzaName))
                .body("price", is(pizzaPrice));
    }

    @Test
    public void shouldReturn404WhenUpdatingPizzaPrice() throws PizzaNotFoundException {
        String pizzaName = "pepperoni";
        int pizzaPrice = 20;

        when(pizzaService.updatePizzaPrice(pizzaName, pizzaPrice)).thenThrow(new PizzaNotFoundException(pizzaName + " is not on our menu"));

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