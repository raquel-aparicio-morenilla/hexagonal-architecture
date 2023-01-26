package com.raquel.hexagon.infrastructure.inputAdapter;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JsonPizza {

    private String name;

    private int price;
}
