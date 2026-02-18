import org.junit.jupiter.api.Test;
import org.lucidity.entities.Location;
import org.lucidity.entities.Order;
import org.lucidity.service.RouteOptimizer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RouteOptimizerTest {

    @Test
    void singleOrder_withWaiting() {
        RouteOptimizer optimizer = new RouteOptimizer();
        Location start = new Location(0, 0);
        // Restaurant very close, consumer slightly farther
        Order order = new Order(
                0,
                new Location(0, 0.001),
                new Location(0, 0.002),
                10
        );
        double result = optimizer.findMinimumCompletionTime(
                start,
                List.of(order)
        );
        assertTrue(result >= 10);
    }

    @Test
    void twoOrders_basicRouting() {
        RouteOptimizer optimizer = new RouteOptimizer();
        Location start = new Location(0, 0);
        Order o1 = new Order(
                0,
                new Location(0, 0.001),
                new Location(0, 0.002),
                0
        );
        Order o2 = new Order(
                1,
                new Location(0, 0.003),
                new Location(0, 0.004),
                0
        );
        double result = optimizer.findMinimumCompletionTime(
                start,
                List.of(o1, o2)
        );
        // Should finish in reasonable positive time
        assertTrue(result > 0);
        assertTrue(result < 200);
    }
}

