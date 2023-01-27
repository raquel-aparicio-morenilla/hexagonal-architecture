package com.raquel.hexagon.infrastructure.inputAdapter;

import com.raquel.hexagon.domain.object.Pizza;
import com.raquel.hexagon.domain.object.PizzaNotFoundException;
import com.raquel.hexagon.domain.object.PizzaNotValidException;
import com.raquel.hexagon.domain.useCase.PizzaManager;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/pizzas")
public class PizzaApiResource {

    @Inject
    PizzaManager pizzaManager;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PizzaResponse> getAllPizzas() {
        List<Pizza> pizzaList = pizzaManager.getAllPizzas();
        return pizzaList.stream().map(pizza -> PizzaResponse.builder().name(pizza.getName()).price(pizza.getPrice()).build()).collect(Collectors.toList());
    }


    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public PizzaResponse getPizza(String name) throws PizzaNotFoundException, PizzaNotValidException {
        Pizza pizza = pizzaManager.getPizza(name);
        return PizzaResponse.builder().name(pizza.getName()).price(pizza.getPrice()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public PizzaResponse updatePizzaPrice(JsonPizza jsonPizza) throws PizzaNotFoundException , PizzaNotValidException {
        Pizza pizza = pizzaManager.updatePizzaPrice(jsonPizza.getName(), jsonPizza.getPrice());
        return PizzaResponse.builder().name(pizza.getName()).price(pizza.getPrice()).build();
    }
}