package com.usbcali.reservas.view;

import javax.swing.*;

public class MostrarVentana implements IMostrarVentana {
    @Override
    public String solicitarEntrada(String mensaje) {
        return JOptionPane.showInputDialog(mensaje);
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }
}
