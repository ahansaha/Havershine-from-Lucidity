package org.lucidity.entities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class State {

    private int nodeIndex;

    private int pickedMask;

    private int deliveredMask;

    private double time;
}
