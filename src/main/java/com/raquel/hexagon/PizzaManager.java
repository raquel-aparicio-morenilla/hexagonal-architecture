package com.raquel.hexagon;

import java.util.List;

public interface PizzaManager {

    List<Pizza> getAllPizzas();

    Pizza getPizza(String name) throws PizzaNotFoundException;

    Pizza updatePizzaPrice(String name, int price) throws PizzaNotFoundException;
}
