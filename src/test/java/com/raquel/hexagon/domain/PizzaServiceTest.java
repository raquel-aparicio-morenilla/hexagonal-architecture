package com.raquel.hexagon.domain;


import com.raquel.hexagon.domain.inputPort.PizzaService;
import com.raquel.hexagon.domain.object.Pizza;
import com.raquel.hexagon.domain.outputPort.PizzeriaRepository;
import com.raquel.hexagon.domain.useCase.PizzaManager;
import io.quarkus.test.junit.mockito.InjectMock;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PizzaServiceTest extends PizzaManagerTest{

    @InjectMock
    PizzeriaRepository pizzeriaRepository = mock(PizzeriaRepository.class);

    @Override
    protected PizzaManager getPizzeriaManagerUnderTest() {
        return new PizzaService(pizzeriaRepository);
    }

    @Override
    protected void setUpExpectedPizzas(List<Pizza> pizzaList) {
        when(pizzeriaRepository.getAllPizzas()).thenReturn(pizzaList);
    }

    @Override
    protected void setUpExpectedPizza(Pizza pizza) {
        if(pizza != null) {
            Optional<Pizza> expectedResult = Optional.of(pizza);
            when(pizzeriaRepository.getPizza(pizza.getName())).thenReturn(expectedResult);
        }
    }
}
