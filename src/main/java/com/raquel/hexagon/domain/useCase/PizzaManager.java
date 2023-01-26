package com.raquel.hexagon.domain.useCase;

import com.raquel.hexagon.domain.object.Pizza;
import com.raquel.hexagon.domain.object.PizzaNotFoundException;

import java.util.List;

public interface PizzaManager {

    List<Pizza> getAllPizzas();

    Pizza getPizza(String name) throws PizzaNotFoundException;

    Pizza updatePizzaPrice(String name, int price) throws PizzaNotFoundException;
}
