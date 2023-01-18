package com.raquel.hexagon;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PizzaResponse {

    private String name;
    private int price;
}
