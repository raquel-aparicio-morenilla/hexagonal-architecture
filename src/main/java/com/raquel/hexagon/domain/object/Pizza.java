package com.raquel.hexagon.domain.object;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class Pizza {

    private String name;
    @Setter
    private int price;
}
