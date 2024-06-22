package com.licenta.rapoarteimobiliare.DTO;

import java.util.Set;

public class PreferenceDTO {
    private String tipImobil;
    private Integer numarCamere;
    private Integer suprafataMinima;
    private Integer anConstructie;
    private Set<String> facilitati;

    public String getTipImobil() {
        return tipImobil;
    }

    public void setTipImobil(String tipImobil) {
        this.tipImobil = tipImobil;
    }

    public Integer getNumarCamere() {
        return numarCamere;
    }

    public void setNumarCamere(Integer numarCamere) {
        this.numarCamere = numarCamere;
    }

    public Integer getSuprafataMinima() {
        return suprafataMinima;
    }

    public void setSuprafataMinima(Integer suprafataMinima) {
        this.suprafataMinima = suprafataMinima;
    }

    public Integer getAnConstructie() {
        return anConstructie;
    }

    public void setAnConstructie(Integer anConstructie) {
        this.anConstructie = anConstructie;
    }

    public Set<String> getFacilitati() {
        return facilitati;
    }

    public void setFacilitati(Set<String> facilitati) {
        this.facilitati = facilitati;
    }
}
