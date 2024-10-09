package com.usbcali.reservas.repository;

import com.usbcali.reservas.domain.Reserva;

import java.util.ArrayList;
import java.util.List;

public class ReservaRepository implements IReservaRepository {
    private List<Reserva> reservas;

    public ReservaRepository(){
        this.reservas = new ArrayList<>();
    }

    @Override
    public void agregarReserva(Reserva reserva) {
        reservas.add(reserva);
    }

    @Override
    public List<Reserva> obtenerReservas() {
        return new ArrayList<>(reservas);
    }
}
