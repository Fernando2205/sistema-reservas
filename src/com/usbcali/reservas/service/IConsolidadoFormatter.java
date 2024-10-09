package com.usbcali.reservas.service;

import com.usbcali.reservas.domain.Reserva;

import java.util.List;

public interface IConsolidadoFormatter {
    String formatearConsolidado(List<Reserva> reservas);
}
