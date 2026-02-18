# Delivery Route Optimizer

This project computes the minimum time required for a courier to pick up and deliver multiple orders.

Approach:
- Model each moment as a state: location + picked orders + delivered orders
- Use Dijkstra over this state space since all costs are non-negative
- Precompute travel times using haversine distance
- Bitmask used to track picked/delivered orders

Assumptions:
- Single rider
- Speed = 20 km/h
- Order IDs assumed 0..N-1 (or internally mapped)

Tradeoffs:
- State space grows exponentially with orders
- Works well for moderate N
- In production, heuristics or batching would be used

Includes:
- Core optimizer
- Small unit tests
