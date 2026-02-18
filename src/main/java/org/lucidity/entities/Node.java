package org.lucidity.entities;

import lombok.*;
import org.lucidity.constants.NodeType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Node {

    private NodeType type;

    private int orderId;

    private Location location;
}
