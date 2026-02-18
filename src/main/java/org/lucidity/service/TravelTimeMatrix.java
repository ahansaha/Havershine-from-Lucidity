package org.lucidity.service;

import org.lucidity.entities.Node;

import java.util.List;

public class TravelTimeMatrix {

    private final double[][] matrix;

    public TravelTimeMatrix(List<Node> nodes) {
        int n = nodes.size();
        matrix = new double[n][n];
        //pre-compute so that repeated havershine calculation is not needed
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Haversine.travelMinutes(
                        nodes.get(i).getLocation(),
                        nodes.get(j).getLocation()
                );
            }
        }
    }

    public double get(int i, int j) {
        return matrix[i][j];
    }
}
