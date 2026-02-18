# Delivery Route Optimizer

## Overview
This project computes the minimum time required for a delivery executive to complete a batch of pickup and drop orders.
Each order consists of:
- a pickup location (restaurant)
- a drop location (consumer)
- a preparation time at the restaurant

The delivery executive starts from a given location and can carry multiple orders simultaneously.
Travel time between locations is calculated using the haversine formula with an assumed constant speed of 20 km/h.
The goal is to determine the order of pickups and deliveries that results in the shortest overall completion time.
---

## Approach
The problem is modeled as a shortest-path search over states rather than just physical locations.
A state captures:
- the courier’s current location
- which orders have been picked up
- which orders have been delivered
- the current elapsed time

From any state, the courier can:
- travel to a restaurant to pick up an order (if not already picked)
- travel to a consumer to deliver an order (only if the order has been picked up)

To find the optimal sequence of actions, the implementation uses a Dijkstra-style search over these states.  
This works because all costs in the system are NON-NEGATIVE:
- travel time is non-negative
- waiting time for food preparation is non-negative

This guarantees that the first time we reach a state where all orders are delivered, we have found the optimal completion time.
To keep the search efficient:
- Travel times between all nodes are precomputed once.
- Previously visited states are skipped if they are reached again with a worse time.
- Bitmasks are used to efficiently track which orders are picked and delivered.
---

## Assumptions
- Single delivery executive.
- Unlimited carrying capacity.
- All restaurants start preparing food at time 0.
- Travel speed is constant at 20 km/h.
- No traffic variation is considered.
- No delivery deadlines or time windows.
- Order IDs are mapped internally to indices for bitmask tracking.
---

## Trade-offs and Scalability
The number of possible states grows exponentially with the number of orders, which is expected for optimal routing problems of this nature.

This implementation is suitable for moderate batch sizes and prioritizes correctness and clarity.  
In a production system handling larger volumes, typical extensions would include:
- heuristic search (e.g., A* with admissible heuristics)
- batching and clustering strategies
- capacity constraints
- multiple delivery executives
- traffic-aware travel times
- delivery deadlines
---