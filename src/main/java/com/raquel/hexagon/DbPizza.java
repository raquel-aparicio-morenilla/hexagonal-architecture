package com.raquel.hexagon;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DbPizza {

    private String name;
    private int price;
}
