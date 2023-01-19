package com.raquel.hexagon;

import lombok.AllArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@AllArgsConstructor
public class PizzaService implements PizzaManager {

    @Inject
    PizzeriaRepository pizzeriaRepository;

    @Override
    public List<Pizza> getAllPizzas() {
        return pizzeriaRepository.getAllPizzas();
    }

    public Pizza getPizza(String name) throws PizzaNotFoundException {
        Optional<Pizza> optPizza = pizzeriaRepository.getPizza(name);
        if(optPizza.isEmpty()){
            throw new PizzaNotFoundException(name + " is not on our menu");
        }
        return optPizza.get();
    }

    @Override
    public Pizza updatePizzaPrice(String name, int price) throws PizzaNotFoundException {
        Optional<Pizza> optPizza = pizzeriaRepository.getPizza(name);
        if(optPizza.isEmpty()){
            throw new PizzaNotFoundException(name + " is not on our menu");
        }
        Pizza pizza = optPizza.get();
        pizza.setPrice(price);
        pizzeriaRepository.storePizza(pizza);
        return getPizza(name);
    }
}
