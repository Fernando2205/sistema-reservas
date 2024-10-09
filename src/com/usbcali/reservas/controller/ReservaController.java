package com.usbcali.reservas.controller;

import com.usbcali.reservas.domain.Localidad;
import com.usbcali.reservas.domain.Reserva;
import com.usbcali.reservas.service.ReservaService;
import com.usbcali.reservas.view.IUserInputHandler;
import com.usbcali.reservas.util.DateUtil;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ReservaController {
    private ReservaService reservaService;
    private IUserInputHandler inputHandler;

    public ReservaController(ReservaService service, IUserInputHandler inputHandler){
        this.reservaService = service;
        this.inputHandler = inputHandler;
    }

    public void iniciar(){
        while(true){
            String opcion = inputHandler.solicitarEntrada("Seleccione una opción:\n1. Reservar \n2. Consultar estado de reservas \n3. Consolidado\n4. Salir");
                    switch (opcion){
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
                            inputHandler.mostrarMensaje("Opción no valida");
                    }
        }

    }
    private void hacerReserva(){
        LocalDate fecha = solicitarFecha();
        Localidad localidad = solicitarLocalidad();
        int cantidad = solicitarCantidad();

        try{
            Reserva reserva = new Reserva(fecha,localidad, cantidad);
            reservaService.hacerReserva(reserva);
            inputHandler.mostrarMensaje( "Reserva realizada:\nFecha: " + fecha.format(DateUtil.DATE_FORMATTER) +
                    "\nLocalidad: " + localidad + "\nCantidad: " + cantidad);
        }catch (RuntimeException e){
            inputHandler.mostrarMensaje(e.getMessage());
        }

    }
    private LocalDate solicitarFecha(){
        while (true) {
            try {
                String fechasDisponibles = DateUtil.FECHAS_CONCIERTO.stream()
                        .map(fecha -> fecha.format(DateUtil.DATE_FORMATTER))
                        .collect(Collectors.joining(", "));

                String fechaStr = inputHandler.solicitarEntrada("¿En qué fecha desea asistir? (DD-MM-YYYY)\n" +
                        "Fechas disponibles: " + fechasDisponibles);
                LocalDate fecha = LocalDate.parse(fechaStr, DateUtil.DATE_FORMATTER);
                if (DateUtil.FECHAS_CONCIERTO.contains(fecha)) {
                    return fecha;
                }
                inputHandler.mostrarMensaje("Fecha no disponible para el concierto. Por favor, elija una fecha válida.");
            } catch (DateTimeParseException e) {
                inputHandler.mostrarMensaje("Formato de fecha inválido. Por favor, use el formato DD-MM-YYYY.");
            }
        }
    }

    private Localidad solicitarLocalidad() {
        while (true) {
            String localidadesDisponibles = Arrays.stream(Localidad.values())
                    .map(Localidad::toString)
                    .collect(Collectors.joining(", "));

            String localidadStr = inputHandler.solicitarEntrada("¿Qué tipo de localidad desea? " +
                    String.join(", ",localidadesDisponibles));
            try {
                return Localidad.valueOf(localidadStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                inputHandler.mostrarMensaje("Localidad inválida. Por favor, elija entre " +
                        String.join(", ",localidadesDisponibles));
            }
        }
    }

    private int solicitarCantidad() {
        while (true) {
            try {
                String cantidadStr = inputHandler.solicitarEntrada("¿Cuántas boletas quiere?");
                int cantidad = Integer.parseInt(cantidadStr);
                if (cantidad > 0) {
                    return cantidad;
                }
                inputHandler.mostrarMensaje("La cantidad debe ser un número positivo.");
            } catch (NumberFormatException e) {
                inputHandler.mostrarMensaje("Por favor, ingrese un número válido.");
            }
        }
    }

    private void mostrarConsolidado() {
        inputHandler.mostrarMensaje(reservaService.obtenerConsolidado());
    }
    private void mostrarEstado() {
        inputHandler.mostrarMensaje(reservaService.obtenerEstado());
    }
}
