package com.usbcali.reservas.domain;

public enum Localidad {
    VIP,
    PALCO,
    GRAMILLA;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }

}
