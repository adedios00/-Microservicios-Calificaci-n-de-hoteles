package com.msvc.calificacion.service;

import com.msvc.calificacion.entity.Calificacion;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CalificacionService {
    Calificacion create(Calificacion calificacion);

    List<Calificacion> getCalificaciones();

    List<Calificacion> getCalificacionesByUsuarioId(String usuarioId);

    List<Calificacion> getCalificacionesByHotelId(String hotelId);
}
