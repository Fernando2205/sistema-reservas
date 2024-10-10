package com.usbcali.reservas.controller;

import com.usbcali.reservas.domain.Localidad;
import com.usbcali.reservas.domain.Reserva;
import com.usbcali.reservas.service.ReservaService;
import com.usbcali.reservas.view.IMostrarVentana;
import com.usbcali.reservas.util.DateUtil;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReservaController {
    private ReservaService reservaService;
    private IMostrarVentana ventana;

    public ReservaController(ReservaService service, IMostrarVentana ventana) {
        this.reservaService = service;
        this.ventana = ventana;
    }

    public void iniciar() {
        while (true) {
            String opcion = ventana.solicitarEntrada(
                    "Seleccione una opción:\n1. Reservar \n2. Consultar estado de reservas \n3. Consolidado\n4. Salir");
            switch (opcion) {
                case "1":
                    hacerReserva();
                    break;
                case "2":
                    mostrarEstado();
                    break;
                case "3":
                    mostrarConsolidado();
                    break;
                case "4":
                    return;
                default:
                    ventana.mostrarMensaje("Opción no valida");
            }
        }

    }

    private void hacerReserva() {
        LocalDate fecha = solicitarFecha();
        Localidad localidad = solicitarLocalidad();
        int cantidad = solicitarCantidad();

        try {
            Reserva reserva = new Reserva(fecha, localidad, cantidad);
            reservaService.hacerReserva(reserva);
            ventana.mostrarMensaje("Reserva realizada:\nFecha: " + fecha.format(DateUtil.DATE_FORMATTER) +
                    "\nLocalidad: " + localidad + "\nCantidad: " + cantidad);
        } catch (RuntimeException e) {
            ventana.mostrarMensaje(e.getMessage());
        }

    }

    private LocalDate solicitarFecha() {
        while (true) {
            try {
                List<String> fechasDisponibles = DateUtil.FECHAS_CONCIERTO.stream()
                        .map(fecha -> fecha.format(DateUtil.DATE_FORMATTER))
                        .collect(Collectors.toList());

                String fechaStr = ventana.solicitarEntrada("¿En qué fecha desea asistir? (DD-MM-YYYY)\n" +
                        "Fechas disponibles: \n" + String.join("\n", fechasDisponibles));
                LocalDate fecha = LocalDate.parse(fechaStr, DateUtil.DATE_FORMATTER);
                if (DateUtil.FECHAS_CONCIERTO.contains(fecha)) {
                    return fecha;
                }
                ventana
                        .mostrarMensaje("Fecha no disponible para el concierto. Por favor, elija una fecha válida.");
            } catch (DateTimeParseException e) {
                ventana.mostrarMensaje("Formato de fecha inválido. Por favor, use el formato DD-MM-YYYY.");
            }
        }
    }

    private Localidad solicitarLocalidad() {
        while (true) {
            String localidadesDisponibles = Arrays.stream(Localidad.values())
                    .map(Localidad::toString)
                    .collect(Collectors.joining(", "));

            String localidadStr = ventana.solicitarEntrada("¿Qué tipo de localidad desea? " +
                    String.join(", ", localidadesDisponibles));
            try {
                return Localidad.valueOf(localidadStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                ventana.mostrarMensaje("Localidad inválida. Por favor, elija entre " +
                        String.join(", ", localidadesDisponibles));
            }
        }
    }

    private int solicitarCantidad() {
        while (true) {
            try {
                String cantidadStr = ventana.solicitarEntrada("¿Cuántas boletas quiere?");
                int cantidad = Integer.parseInt(cantidadStr);
                if (cantidad > 0) {
                    return cantidad;
                }
                ventana.mostrarMensaje("La cantidad debe ser un número positivo.");
            } catch (NumberFormatException e) {
                ventana.mostrarMensaje("Por favor, ingrese un número válido.");
            }
        }
    }

    private void mostrarConsolidado() {
        ventana.mostrarMensaje(reservaService.obtenerConsolidado());
    }

    private void mostrarEstado() {
        ventana.mostrarMensaje(reservaService.obtenerEstado());
    }
}
