package com.usbcali.reservas.service;

import com.sun.source.tree.ReturnTree;
import com.usbcali.reservas.domain.Localidad;
import com.usbcali.reservas.domain.Reserva;
import com.usbcali.reservas.util.DateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConsolidadoFormatter implements IConsolidadoFormatter {
    @Override
    public String formatearConsolidado(List<Reserva> reservas) {
        //Lista donde se almacena la informacion del consolidado
        List<String> consolidadoLista = new ArrayList<>();

        for (LocalDate fecha : DateUtil.FECHAS_CONCIERTO){
            consolidadoLista.add("Fecha: " + fecha.format(DateUtil.DATE_FORMATTER));

            // Filtrar las reservas para la fecha actual
            List<Reserva> reservasEnFecha = new ArrayList<>();
            for (Reserva reserva : reservas) {
                if (reserva.getFecha().equals(fecha)) {
                    reservasEnFecha.add(reserva);
                }
            }

            // Sumar las cantidades por localidad
            for (Localidad localidad : Localidad.values()) {
                int totalCantidad = reservasEnFecha.stream()
                        .filter(reserva -> reserva.getLocalidad() == localidad)
                        .mapToInt(Reserva::getCantidad)
                        .sum();

                consolidadoLista.add(localidad + ": " + totalCantidad);
            }

            consolidadoLista.add(""); // Agregar una línea en blanco para separar fechas
        }
        return String.join("\n", consolidadoLista);
    }
}
