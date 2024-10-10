package com.usbcali.reservas.service;

import com.usbcali.reservas.domain.Reserva;
import com.usbcali.reservas.repository.IReservaRepository;
import com.usbcali.reservas.util.DateUtil;

import java.util.List;

public class ReservaService {
    private IReservaRepository repositorio;
    private IJsonFormatter formateador;
    private IConsolidadoFormatter consolidado;

    public ReservaService(IReservaRepository reposotorio, IJsonFormatter formateador,
            IConsolidadoFormatter consolidado) {
        this.repositorio = reposotorio;
        this.formateador = formateador;
        this.consolidado = consolidado;
    }

    public void hacerReserva(Reserva reserva) {
        if (!DateUtil.FECHAS_CONCIERTO.contains(reserva.getFecha())) {
            throw new RuntimeException("Fecha no valida");
        }
        repositorio.agregarReserva(reserva);
    }

    public String obtenerEstado() {
        List<Reserva> reservas = repositorio.obtenerReservas();
        return formateador.formatearEstado(reservas);
    }

    public String obtenerConsolidado() {
        List<Reserva> reservas = repositorio.obtenerReservas();
        return consolidado.formatearConsolidado(reservas);
    }
}
