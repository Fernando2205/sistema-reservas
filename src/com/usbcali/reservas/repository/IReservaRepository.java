package com.usbcali.reservas.repository;

import com.usbcali.reservas.domain.Reserva;

import java.util.List;

public interface IReservaRepository {
    void agregarReserva(Reserva reserva);
    List<Reserva> obtenerReservas();

}
