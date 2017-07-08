package com.sinergia.registroepisodiosdemigrana.Activities.Dto;

import java.util.Date;

/**
 * Created by juan on 8/07/17.
 */

public class Episodio {
    private Date fechaHora;
    private String nivelDolor;
    private String localiacion;
    private String intencidad;
    private String patronesSueno;
    private String actividadFisica;
    private String medicamentos;
    private String bebidas;

    public Episodio(){};

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getNivelDolor() {
        return nivelDolor;
    }

    public void setNivelDolor(String nivelDolor) {
        this.nivelDolor = nivelDolor;
    }

    public String getLocaliacion() {
        return localiacion;
    }

    public void setLocaliacion(String localiacion) {
        this.localiacion = localiacion;
    }

    public String getIntencidad() {
        return intencidad;
    }

    public void setIntencidad(String intencidad) {
        this.intencidad = intencidad;
    }

    public String getPatronesSueno() {
        return patronesSueno;
    }

    public void setPatronesSueno(String patronesSueno) {
        this.patronesSueno = patronesSueno;
    }

    public String getActividadFisica() {
        return actividadFisica;
    }

    public void setActividadFisica(String actividadFisica) {
        this.actividadFisica = actividadFisica;
    }

    public String getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(String medicamentos) {
        this.medicamentos = medicamentos;
    }

    public String getBebidas() {
        return bebidas;
    }

    public void setBebidas(String bebidas) {
        this.bebidas = bebidas;
    }
}
