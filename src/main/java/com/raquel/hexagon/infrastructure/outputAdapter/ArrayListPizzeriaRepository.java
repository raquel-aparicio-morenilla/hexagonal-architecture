package com.raquel.hexagon.infrastructure.outputAdapter;

import com.raquel.hexagon.domain.object.Pizza;
import com.raquel.hexagon.domain.outputPort.PizzeriaRepository;
import io.quarkus.arc.DefaultBean;
import io.quarkus.arc.profile.IfBuildProfile;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
@DefaultBean
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
