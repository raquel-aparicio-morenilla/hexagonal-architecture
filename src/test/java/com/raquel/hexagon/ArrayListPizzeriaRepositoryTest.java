package com.raquel.hexagon;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArrayListPizzeriaRepositoryTest {

    ArrayListPizzeriaRepository arrayListPizzeriaRepository = new ArrayListPizzeriaRepository();

    @BeforeEach
    void setUp() {
        arrayListPizzeriaRepository.storePizza(new Pizza("pepperoni", 15));
        arrayListPizzeriaRepository.storePizza(new Pizza("barbeque", 19));
    }

    @AfterEach
    void tearDown() {
        arrayListPizzeriaRepository.resetSystem();
    }


    @Test
    void shouldGetAllPizzas() {
        List<Pizza> allPizzas = arrayListPizzeriaRepository.getAllPizzas();
        assertThat(allPizzas.size()).isEqualTo(2);
    }

    @Test
    void shouldReturnPizzaWhenGetPizza() {
        String pizzaName = "pepperoni";
        Optional<Pizza> pizzaResult = arrayListPizzeriaRepository.getPizza(pizzaName);
        assertTrue(pizzaResult.isPresent());
        assertThat(pizzaResult.get().getName()).isEqualTo(pizzaName);
        assertThat(pizzaResult.get().getPrice()).isEqualTo(15);
    }

    @Test
    void shouldReturnEmptyOptionalWhenGetPizza() {
        String pizzaName = "carbonara";
        Optional<Pizza> pizza = arrayListPizzeriaRepository.getPizza(pizzaName);
        assertTrue(pizza.isEmpty());
    }

    @Test
    void shouldStorePizza() {
        String pizzaName = "carbonara";
        int pizzaPrice = 20;
        Pizza pizza = Pizza.builder().name(pizzaName).price(pizzaPrice).build();
        arrayListPizzeriaRepository.storePizza(pizza);

        Optional<Pizza> pizzaResult = arrayListPizzeriaRepository.getPizza(pizzaName);
        assertTrue(pizzaResult.isPresent());
        assertThat(pizzaResult.get().getName()).isEqualTo(pizzaName);
        assertThat(pizzaResult.get().getPrice()).isEqualTo(pizzaPrice);
    }

    @Test
    void shouldStoreOrder() {
        //arrayListPizzeriaRepository.storeOrder();
    }
}