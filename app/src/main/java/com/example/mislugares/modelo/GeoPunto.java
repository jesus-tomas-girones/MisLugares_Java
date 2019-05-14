package com.example.mislugares.modelo;

import java.util.Objects;

public class GeoPunto {

    private double longitud, latitud;

    static public GeoPunto SIN_POSICION = new GeoPunto(0.0,0.0);

    public GeoPunto(double longitud, double latitud) {
        this.longitud= longitud;
        this.latitud= latitud;
    }
    public String toString() {
        return new String("longitud:" + longitud + ", latitud:"+ latitud);
    }
    public double distancia(GeoPunto punto) {
        final double RADIO_TIERRA = 6371000; // en metros
        double dLat = Math.toRadians(latitud - punto.latitud);
        double dLon = Math.toRadians(longitud - punto.longitud);
        double lat1 = Math.toRadians(punto.latitud);
        double lat2 = Math.toRadians(latitud);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.sin(dLon/2) * Math.sin(dLon/2) *
                        Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return c * RADIO_TIERRA;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeoPunto geoPunto = (GeoPunto) o;
        return Double.compare(geoPunto.longitud, longitud) == 0 &&
                Double.compare(geoPunto.latitud, latitud) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(longitud, latitud);
    }


}
