package org.lucidity.service;

import org.lucidity.constants.NodeType;
import org.lucidity.entities.*;

import java.util.*;
import java.util.stream.Collectors;

public class RouteOptimizer {

    public double findMinimumCompletionTime(Location start, List<Order> orders) {

        List<Node> nodes = new ArrayList<>();
        nodes.add(new Node(NodeType.START, -1, start));

        for (Order o : orders) {
            nodes.add(new Node(NodeType.PICKUP, o.getId(), o.getRestaurant()));
            nodes.add(new Node(NodeType.DROP, o.getId(), o.getConsumer()));
        }

        Map<Integer, Order> orderMap = orders.stream()
                .collect(Collectors.toMap(Order::getId, o -> o));

        TravelTimeMatrix matrix = new TravelTimeMatrix(nodes);

        PriorityQueue<State> pq = new PriorityQueue<>(
                Comparator.comparingDouble(State::getTime)
        );

        pq.add(new State(0, 0, 0, 0));

        Map<StateKey, Double> bestSeen = new HashMap<>();
        int fullMask = (1 << orders.size()) - 1;

        while (!pq.isEmpty()) {
            State cur = pq.poll();

            if (cur.getDeliveredMask() == fullMask) {
                return cur.getTime();
            }

            StateKey key = new StateKey(
                    cur.getNodeIndex(),
                    cur.getPickedMask(),
                    cur.getDeliveredMask()
            );

            //if same situation we have seen earlier and faster
            if (bestSeen.containsKey(key) && bestSeen.get(key) <= cur.getTime()) {
                continue;
            }
            bestSeen.put(key, cur.getTime());

            for (int i = 1; i < nodes.size(); i++) {

                Node next = nodes.get(i);
                int bit = 1 << next.getOrderId();

                // Pick up
                if (next.getType() == NodeType.PICKUP) {
                    //Skip if already picked
                    if ((cur.getPickedMask() & bit) != 0) continue;

                    double travel = matrix.get(cur.getNodeIndex(), i);
                    double arrive = cur.getTime() + travel;

                    Order order = orderMap.get(next.getOrderId());
                    double ready = order.getPrepTime();

                    //Reaching before order is ready and then wait till prep time
                    double newTime = Math.max(arrive, ready);

                    pq.add(new State(
                            i,
                            cur.getPickedMask() | bit,
                            cur.getDeliveredMask(),
                            newTime
                    ));
                }

                // Drop
                if (next.getType() == NodeType.DROP) {

                    //Can't deliver if not picked
                    if ((cur.getPickedMask() & bit) == 0) continue;

                    //Skip if already delivered.
                    if ((cur.getDeliveredMask() & bit) != 0) continue;

                    double travel = matrix.get(cur.getNodeIndex(), i);
                    double newTime = cur.getTime() + travel;

                    pq.add(new State(
                            i,
                            cur.getPickedMask(),
                            cur.getDeliveredMask() | bit,
                            newTime
                    ));
                }
            }
        }
        // Should not normally happen.
        return Double.MAX_VALUE;
    }
}
