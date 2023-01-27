package com.raquel.hexagon.infrastructure;

import com.raquel.hexagon.domain.object.Pizza;
import com.raquel.hexagon.domain.outputPort.PizzeriaRepository;
import com.raquel.hexagon.infrastructure.inputAdapter.JsonPizza;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.QuarkusTestProfile;
import io.quarkus.test.junit.TestProfile;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;


@QuarkusTest
@TestProfile(PizzaApiResourceIT.CustomTestProfile.class)
public class PizzaApiResourceIT {


    public static class CustomTestProfile implements QuarkusTestProfile {

        @Override
        //This overrides the quarkusProfile being set.
        // By default during tests, this will be "test".
        public String getConfigProfile() {
            return "test";
        }
    }


    private final String PEPPERONI_PIZZA_NAME = "pepperoni";
    private final int PEPPERONI_PIZZA_PRICE = 15;
    private final String BARBEQUE_PIZZA_NAME = "barbeque";
    private final String INVALID_PIZZA_NAME = "invalidPizza";
    private final int BARBEQUE_PIZZA_PRICE = 19;

    @Inject
    PizzeriaRepository pizzeriaRepository;

    @BeforeEach
    void setUp() {
        pizzeriaRepository.storePizza(Pizza.builder().name(PEPPERONI_PIZZA_NAME).price(PEPPERONI_PIZZA_PRICE).build());
        pizzeriaRepository.storePizza(Pizza.builder().name(BARBEQUE_PIZZA_NAME).price(BARBEQUE_PIZZA_PRICE).build());
        pizzeriaRepository.storePizza(Pizza.builder().name(INVALID_PIZZA_NAME).price(0).build());
    }

    @AfterEach
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
    public void shouldReturn3ItemListWhenGettingAllPizzas() {
        given().contentType(ContentType.JSON)
                .when().get("/pizzas")
                .then()
                .statusCode(200)
                .body("$.size()", is(3));
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
    public void shouldReturn403WhenUpdatingPizzaToNoPrice() {
        String pizzaName = PEPPERONI_PIZZA_NAME;
        int pizzaPrice = 0;

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

    @Test
    public void shouldReturn403WhenGettingPizzaWithNoPrice() {
        given().contentType(ContentType.JSON)
                .when().get("/pizzas/invalidPizza")
                .then()
                .statusCode(403);
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