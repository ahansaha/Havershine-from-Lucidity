package org.lucidity.entities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    private int id;

    private Location restaurant;

    private Location consumer;

    private double prepTime; //assumed in mins
}

