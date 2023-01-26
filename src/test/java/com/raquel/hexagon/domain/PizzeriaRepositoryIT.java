package com.raquel.hexagon.domain;

import com.raquel.hexagon.domain.object.Pizza;
import com.raquel.hexagon.domain.outputPort.PizzeriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class PizzeriaRepositoryIT {

    private final String PEPPERONI_PIZZA_NAME = "pepperoni";
    private final int PEPPERONI_PIZZA_PRICE = 15;
    private final String BARBEQUE_PIZZA_NAME = "barbeque";
    private final int BARBEQUE_PIZZA_PRICE = 19;

    PizzeriaRepository pizzeriaRepository;

    protected abstract PizzeriaRepository getPizzeriaRepositoryUnderTest();

    @BeforeEach
    void setUp() {
        pizzeriaRepository = getPizzeriaRepositoryUnderTest();

        pizzeriaRepository.storePizza(Pizza.builder().name(PEPPERONI_PIZZA_NAME).price(PEPPERONI_PIZZA_PRICE).build());
        pizzeriaRepository.storePizza(Pizza.builder().name(BARBEQUE_PIZZA_NAME).price(BARBEQUE_PIZZA_PRICE).build());
    }

    @Test
    public void shouldGetAllPizzas() {
        List<Pizza> allPizzas = pizzeriaRepository.getAllPizzas();
        assertThat(allPizzas.size()).isEqualTo(2);
    }

    @Test
    void shouldReturnPizzaWhenGetPizza() {
        String pizzaName = PEPPERONI_PIZZA_NAME;
        Optional<Pizza> pizzaResult = pizzeriaRepository.getPizza(pizzaName);
        assertTrue(pizzaResult.isPresent());
        assertThat(pizzaResult.get().getName()).isEqualTo(pizzaName);
        assertThat(pizzaResult.get().getPrice()).isEqualTo(PEPPERONI_PIZZA_PRICE);
    }

    @Test
    void shouldReturnEmptyOptionalWhenGetPizza() {
        String pizzaName = "carbonara";
        Optional<Pizza> pizza = pizzeriaRepository.getPizza(pizzaName);
        assertTrue(pizza.isEmpty());
    }

    @Test
    void shouldStorePizza() {
        String pizzaName = "carbonara";
        int pizzaPrice = 20;

        Optional<Pizza> pizzaOpt = pizzeriaRepository.getPizza(pizzaName);
        assertTrue(pizzaOpt.isEmpty());

        Pizza pizza = Pizza.builder().name(pizzaName).price(pizzaPrice).build();
        pizzeriaRepository.storePizza(pizza);

        Optional<Pizza> pizzaResult = pizzeriaRepository.getPizza(pizzaName);
        assertTrue(pizzaResult.isPresent());
        assertThat(pizzaResult.get().getName()).isEqualTo(pizzaName);
        assertThat(pizzaResult.get().getPrice()).isEqualTo(pizzaPrice);
    }

    @Test
    void shouldUpdatePizzaPrice() {
        String pizzaName = PEPPERONI_PIZZA_NAME;
        int pizzaPrice = PEPPERONI_PIZZA_PRICE + 1;

        Optional<Pizza> pizzaResult = pizzeriaRepository.getPizza(pizzaName);
        assertTrue(pizzaResult.isPresent());
        assertThat(pizzaResult.get().getName()).isEqualTo(pizzaName);
        assertThat(pizzaResult.get().getPrice()).isEqualTo(PEPPERONI_PIZZA_PRICE);

        Pizza pizza = Pizza.builder().name(pizzaName).price(pizzaPrice).build();
        pizzeriaRepository.storePizza(pizza);

        pizzaResult = pizzeriaRepository.getPizza(pizzaName);
        assertTrue(pizzaResult.isPresent());
        assertThat(pizzaResult.get().getName()).isEqualTo(pizzaName);
        assertThat(pizzaResult.get().getPrice()).isEqualTo(pizzaPrice);
    }
}