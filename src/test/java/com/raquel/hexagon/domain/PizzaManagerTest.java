package com.raquel.hexagon.domain;

import com.raquel.hexagon.domain.object.Pizza;
import com.raquel.hexagon.domain.object.PizzaNotFoundException;
import com.raquel.hexagon.domain.useCase.PizzaManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

abstract class PizzaManagerTest {

    private final String PEPPERONI_PIZZA_NAME = "pepperoni";
    private final int PEPPERONI_PIZZA_PRICE = 15;
    private final String BARBEQUE_PIZZA_NAME = "barbeque";
    private final int BARBEQUE_PIZZA_PRICE = 19;

    PizzaManager pizzaManager;

    protected abstract PizzaManager getPizzeriaManagerUnderTest();

    protected abstract void setUpExpectedPizzas(List<Pizza> pizzaList);

    protected abstract void setUpExpectedPizza(Pizza pizza);

    @BeforeEach
    void setUp() {
        pizzaManager = getPizzeriaManagerUnderTest();
    }

    @Test
    void shouldReturnEmptyListWhenCallingGetAllPizzas() {
        setUpExpectedPizzas(new ArrayList<>());

        List<Pizza> allPizzas = pizzaManager.getAllPizzas();
        assertThat(allPizzas.size()).isEqualTo(0);
    }

    @Test
    void shouldReturn2ItemListWhenCallingGetAllPizzas() {
        setUpExpectedPizzas(Arrays.asList(Pizza.builder().name(PEPPERONI_PIZZA_NAME).price(PEPPERONI_PIZZA_PRICE).build(), Pizza.builder().name(BARBEQUE_PIZZA_NAME).price(BARBEQUE_PIZZA_PRICE).build()));

        List<Pizza> allPizzas = pizzaManager.getAllPizzas();
        assertThat(allPizzas.size()).isEqualTo(2);
    }

    @Test
    public void shouldReturnPizzaWhenGettingExistingPizza() throws PizzaNotFoundException {
        String pizzaName = PEPPERONI_PIZZA_NAME;
        int pizzaPrice = PEPPERONI_PIZZA_PRICE;
        setUpExpectedPizza(Pizza.builder().name(pizzaName).price(pizzaPrice).build());

        Pizza pizza = pizzaManager.getPizza(pizzaName);
        assertThat(pizza.getName()).isEqualTo(pizzaName);
        assertThat(pizza.getPrice()).isEqualTo(pizzaPrice);
    }

    @Test
    public void shouldReturn404WhenGettingNotExistingPizza() {
        String pizzaName = PEPPERONI_PIZZA_NAME;

        assertThatThrownBy(() -> pizzaManager.getPizza(pizzaName))
                .isInstanceOf(PizzaNotFoundException.class);
    }

    @Test
    public void shouldUpdatePizzaPrice() throws PizzaNotFoundException {
        String pizzaName = PEPPERONI_PIZZA_NAME;
        int pizzaPrice = PEPPERONI_PIZZA_PRICE + 1;
        setUpExpectedPizza(Pizza.builder().name(pizzaName).price(PEPPERONI_PIZZA_PRICE).build());

        Pizza pizza = pizzaManager.updatePizzaPrice(pizzaName, pizzaPrice);
        assertThat(pizza.getName()).isEqualTo(pizzaName);
        assertThat(pizza.getPrice()).isEqualTo(pizzaPrice);
    }

    @Test
    public void shouldReturn404WhenUpdatingNotExistingPizzaPrice() {
        String pizzaName = PEPPERONI_PIZZA_NAME;
        int pizzaPrice = PEPPERONI_PIZZA_PRICE;

        assertThatThrownBy(() -> pizzaManager.updatePizzaPrice(pizzaName, pizzaPrice))
                .isInstanceOf(PizzaNotFoundException.class);
    }
}