package com.raquel.hexagon.infrastructure.outputAdapter;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class S3ObjectSummary {
    private String name;
    private int price;
}
