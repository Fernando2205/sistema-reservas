package com.usbcali.reservas.domain;

import java.time.LocalDate;

public class Reserva {
    private LocalDate fecha;
    private Localidad localidad;
    private int cantidad;

    public Reserva(LocalDate fecha,Localidad localidad, int cantidad){
        this.fecha = fecha;
        this.localidad = localidad;
        this.cantidad = cantidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Localidad getLocalidad() {
        return localidad;
    }
}
