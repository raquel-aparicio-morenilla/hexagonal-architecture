package com.raquel.hexagon.infrastructure.inputAdapter;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PizzaResponse {

    private String name;
    private int price;
}
