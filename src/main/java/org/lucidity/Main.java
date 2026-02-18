package org.lucidity;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import org.lucidity.entities.Location;
import org.lucidity.entities.Order;
import org.lucidity.service.RouteOptimizer;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Location start = new Location(12.935, 77.614); //somehwere in kormangla

        //Have used bitmask logic so order id's need to start from zero and be continuos.
        List<Order> orders = List.of(
                new Order(0,
                        new Location(12.93, 77.62),
                        new Location(12.94, 77.63),
                        15
                ),
                new Order(1,
                        new Location(12.91, 77.60),
                        new Location(12.90, 77.59),
                        10
                )
        );
        RouteOptimizer optimizer = new RouteOptimizer();
        double result = optimizer.findMinimumCompletionTime(start, orders);
        System.out.println(result);
    }
}
