package com.raquel.hexagon.infrastructure;

import com.raquel.hexagon.domain.inputPort.PizzaService;
import com.raquel.hexagon.domain.object.Pizza;
import com.raquel.hexagon.domain.object.PizzaNotFoundException;
import com.raquel.hexagon.domain.object.PizzaNotValidException;
import com.raquel.hexagon.infrastructure.inputAdapter.JsonPizza;
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

    private final String PEPPERONI_PIZZA_NAME = "pepperoni";
    private final int PEPPERONI_PIZZA_PRICE = 15;
    private final String BARBEQUE_PIZZA_NAME = "barbeque";
    private final int BARBEQUE_PIZZA_PRICE = 19;

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

        when(pizzaService.getAllPizzas()).thenReturn(Arrays.asList(Pizza.builder().name(PEPPERONI_PIZZA_NAME).price(PEPPERONI_PIZZA_PRICE).build(), Pizza.builder().name(BARBEQUE_PIZZA_NAME).price(BARBEQUE_PIZZA_PRICE).build()));

        given().contentType(ContentType.JSON)
                .when().get("/pizzas")
                .then()
                .statusCode(200)
                .body("$.size()", is(2));
    }

    @Test
    public void shouldReturnPizzaWhenGettingExistingPizza() throws PizzaNotFoundException, PizzaNotValidException {
        String pizzaName = PEPPERONI_PIZZA_NAME;
        int pizzaPrice = PEPPERONI_PIZZA_PRICE;

        Pizza pizza = Pizza.builder().name(pizzaName).price(pizzaPrice).build();
        when(pizzaService.getPizza(pizzaName)).thenReturn(pizza);

        given().contentType(ContentType.JSON)
                .when().get("/pizzas/" +  pizzaName)
                .then()
                .statusCode(200)
                .body("name", is(pizzaName))
                .body("price", is(pizzaPrice));
    }

    @Test
    public void shouldReturn404WhenGettingNotExistingPizza() throws PizzaNotFoundException, PizzaNotValidException {
        String pizzaName = PEPPERONI_PIZZA_NAME;

        when(pizzaService.getPizza(pizzaName)).thenThrow(new PizzaNotFoundException(pizzaName + " is not on our menu"));

        given().contentType(ContentType.JSON)
                .when().get("/pizzas/" +  pizzaName)
                .then()
                .statusCode(404);
    }

    @Test
    public void shouldReturn403WhenGettingPizzaWithInvalidPrice() throws PizzaNotFoundException, PizzaNotValidException {
        String pizzaName = PEPPERONI_PIZZA_NAME;

        when(pizzaService.getPizza(pizzaName)).thenThrow(new PizzaNotValidException("Pizza is not valid, price is below 0."));

        given().contentType(ContentType.JSON)
                .when().get("/pizzas/" +  pizzaName)
                .then()
                .statusCode(403);
    }

    @Test
    public void shouldUpdatePizzaPrice() throws PizzaNotFoundException, PizzaNotValidException {
        String pizzaName = PEPPERONI_PIZZA_NAME;
        int pizzaPrice = PEPPERONI_PIZZA_PRICE;

        Pizza pizza = Pizza.builder().name(pizzaName).price(pizzaPrice).build();
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
    public void shouldReturn404WhenUpdatingPizzaPrice() throws PizzaNotFoundException, PizzaNotValidException {
        String pizzaName = PEPPERONI_PIZZA_NAME;
        int pizzaPrice = PEPPERONI_PIZZA_PRICE;

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

    @Test
    public void shouldReturn403WhenUpdatingPizzaPriceOf0() throws PizzaNotFoundException, PizzaNotValidException {
        String pizzaName = PEPPERONI_PIZZA_NAME;
        int pizzaPrice = PEPPERONI_PIZZA_PRICE;

        when(pizzaService.updatePizzaPrice(pizzaName, pizzaPrice)).thenThrow(new PizzaNotValidException("Pizza is not valid, price is below 0."));

        final JsonPizza jsonPizza = JsonPizza.builder()
                .name(pizzaName)
                .price(pizzaPrice)
                .build();

        given().contentType(ContentType.JSON)
                .body(jsonPizza)
                .when().post("/pizzas/")
                .then()
                .statusCode(403);
    }
}