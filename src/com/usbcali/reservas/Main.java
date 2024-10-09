package com.usbcali.reservas;

import com.usbcali.reservas.controller.ReservaController;
import com.usbcali.reservas.repository.IReservaRepository;
import com.usbcali.reservas.repository.ReservaRepository;
import com.usbcali.reservas.service.*;
import com.usbcali.reservas.view.IUserInputHandler;
import com.usbcali.reservas.view.UserInputHandler;

public class Main {
    public static void main(String[] args) {
        IReservaRepository repository = new ReservaRepository();
        IEstadoReservasFormatter estadoFormatter = new JsonFormatter();
        IConsolidadoFormatter consolidadoFormatter = new ConsolidadoFormatter();
        IUserInputHandler inputHandler = new UserInputHandler();
        ReservaService service = new ReservaService(repository, estadoFormatter, consolidadoFormatter);
        ReservaController controller = new ReservaController(service, inputHandler);
        
        controller.iniciar();


    }
}