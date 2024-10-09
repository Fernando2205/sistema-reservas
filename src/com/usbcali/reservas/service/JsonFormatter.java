package com.usbcali.reservas.service;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.usbcali.reservas.domain.Reserva;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.usbcali.reservas.util.DateUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class JsonFormatter  implements IEstadoReservasFormatter {
    DateTimeFormatter dateFormatter = DateUtil.DATE_FORMATTER;
    private final Gson gson;

    public JsonFormatter() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context) ->
                        context.serialize(src.format(dateFormatter))) // Serialización de LocalDate a String con el formato deseado
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, typeOfT, context) ->
                        LocalDate.parse(json.getAsString(), dateFormatter)) // Deserialización de String a LocalDate con el formato deseado
                .create();
    }

    @Override
    public String formatearEstado(List<Reserva> reservas) {
        return gson.toJson(reservas);
    }
}
