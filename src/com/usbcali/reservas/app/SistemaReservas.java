package com.usbcali.reservas.app;

import com.usbcali.reservas.controller.ReservaController;
import com.usbcali.reservas.repository.IReservaRepository;
import com.usbcali.reservas.repository.ReservaRepository;
import com.usbcali.reservas.service.*;
import com.usbcali.reservas.view.IMostrarVentana;
import com.usbcali.reservas.view.MostrarVentana;

public class SistemaReservas {
    private ReservaController controller;

    public SistemaReservas() {
        IReservaRepository repository = new ReservaRepository();
        IJsonFormatter estadoFormatter = new JsonFormatter();
        IConsolidadoFormatter consolidadoFormatter = new ConsolidadoFormatter();
        IMostrarVentana ventana = new MostrarVentana();
        ReservaService service = new ReservaService(repository, estadoFormatter, consolidadoFormatter);
        this.controller = new ReservaController(service, ventana);

    }

    public static void main(String[] args) {
        SistemaReservas app = new SistemaReservas();
        app.controller.iniciar();

    }
}