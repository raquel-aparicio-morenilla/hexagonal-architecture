package com.raquel.hexagon;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class ArrayListPizzeriaRepository implements PizzeriaRepository {

    Map<String, DbPizza> dbPizzaList = new HashMap<>();

    @Override
    public List<Pizza> getAllPizzas() {
        return dbPizzaList.values().stream().map(dbPizza -> Pizza.builder().name(dbPizza.getName()).price(dbPizza.getPrice()).build()).collect(Collectors.toList());
    }

    @Override
    public Optional<Pizza> getPizza(String name) {
        Optional<DbPizza> optDbPizza = Optional.ofNullable(dbPizzaList.get(name));
        return optDbPizza.map(dbPizza -> Pizza.builder().name(dbPizza.getName()).price(dbPizza.getPrice()).build());
    }

    @Override
    public void storePizza(Pizza pizza){
        DbPizza dbPizza = DbPizza.builder().name(pizza.getName()).price(pizza.getPrice()).build();
        dbPizzaList.put(dbPizza.getName(), dbPizza);
    }

    @Override
    public void resetSystem(){
        removeAllPizzas();
    }

    public void removeAllPizzas() {
        dbPizzaList.clear();
    }
}
