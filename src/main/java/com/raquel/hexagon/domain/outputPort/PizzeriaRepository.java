package com.raquel.hexagon.domain.outputPort;

import com.raquel.hexagon.domain.object.Pizza;

import java.util.List;
import java.util.Optional;

public interface PizzeriaRepository {

    List<Pizza> getAllPizzas();

    Optional<Pizza> getPizza(String name);

    void storePizza(Pizza pizza);

    void resetSystem();
}
