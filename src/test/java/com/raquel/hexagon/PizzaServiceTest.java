package com.raquel.hexagon;

import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PizzaServiceTest {

    @InjectMock
    PizzeriaRepository pizzeriaRepository = mock(PizzeriaRepository.class);

    PizzaService pizzaService = new PizzaService(pizzeriaRepository);

    @Test
    void shouldReturnEmptyListWhenCallingGetAllPizzas() {
        when(pizzeriaRepository.getAllPizzas()).thenReturn(new ArrayList<>());
        List<Pizza> allPizzas = pizzaService.getAllPizzas();

        assertThat(allPizzas.size()).isEqualTo(0);
    }

    @Test
    void shouldReturn2ItemListWhenCallingGetAllPizzas() {
        when(pizzeriaRepository.getAllPizzas()).thenReturn(Arrays.asList(new Pizza("pepperoni", 15), new Pizza("barbeque", 19)));
        List<Pizza> allPizzas = pizzaService.getAllPizzas();
        assertThat(allPizzas.size()).isEqualTo(2);
    }

    @Test
    public void shouldReturnPizzaWhenGettingExistingPizza() throws PizzaNotFoundException {
        String pizzaName = "pepperoni";
        int pizzaPrice = 20;

        Optional<Pizza> expectedResult = Optional.of(new Pizza(pizzaName, pizzaPrice));
        when(pizzeriaRepository.getPizza(pizzaName)).thenReturn(expectedResult);

        Pizza pizza = pizzaService.getPizza(pizzaName);
        assertThat(pizza.getName()).isEqualTo(pizzaName);
        assertThat(pizza.getPrice()).isEqualTo(pizzaPrice);
    }

    @Test
    public void shouldReturn404WhenGettingNotExistingPizza() {
        String pizzaName = "pepperoni";

        assertThatThrownBy(() -> pizzaService.getPizza(pizzaName))
                .isInstanceOf(PizzaNotFoundException.class);
    }

    @Test
    public void shouldUpdatePizzaPrice() throws PizzaNotFoundException {
        String pizzaName = "pepperoni";
        int pizzaPrice = 20;

        Optional<Pizza> expectedResult = Optional.of(new Pizza(pizzaName, pizzaPrice));
        when(pizzeriaRepository.getPizza(pizzaName)).thenReturn(expectedResult);


        Pizza pizza = pizzaService.updatePizzaPrice(pizzaName, pizzaPrice);
        assertThat(pizza.getName()).isEqualTo(pizzaName);
        assertThat(pizza.getPrice()).isEqualTo(pizzaPrice);
    }

    @Test
    public void shouldReturn404WhenUpdatingPizzaPrice() {
        String pizzaName = "pepperoni";
        int pizzaPrice = 20;

        Optional<Pizza> expectedResult = Optional.empty();
        when(pizzeriaRepository.getPizza(pizzaName)).thenReturn(expectedResult);

        assertThatThrownBy(() -> pizzaService.updatePizzaPrice(pizzaName, pizzaPrice))
                .isInstanceOf(PizzaNotFoundException.class);
    }
}