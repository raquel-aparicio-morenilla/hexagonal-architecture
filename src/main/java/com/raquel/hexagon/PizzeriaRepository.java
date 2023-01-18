package com.raquel.hexagon;

import java.util.List;
import java.util.Optional;

public interface PizzeriaRepository {

    List<Pizza> getAllPizzas();

    Optional<Pizza> getPizza(String name);

    void storePizza(Pizza pizza);

    boolean storeOrder(Order order);
}
