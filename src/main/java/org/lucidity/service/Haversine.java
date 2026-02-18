package org.lucidity.service;
import org.lucidity.entities.Location;

public class Haversine {
    private static final double R = 6371.0; // earth radius km
    private static final double SPEED_KMPH = 20.0;

    public static double distanceKm(Location a, Location b) {
        double dLat = Math.toRadians(b.getLat() - a.getLat());
        double dLon = Math.toRadians(b.getLon() - a.getLon());

        double lat1 = Math.toRadians(a.getLat());
        double lat2 = Math.toRadians(b.getLat());

        double h = Math.sin(dLat/2) * Math.sin(dLat/2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.sin(dLon/2) * Math.sin(dLon/2);

        return 2 * R * Math.asin(Math.sqrt(h));
    }

    public static double travelMinutes(Location a, Location b) {
        double km = distanceKm(a, b);
        return (km / SPEED_KMPH) * 60.0;
    }
}

