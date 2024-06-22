package com.licenta.rapoarteimobiliare.DTO;

import com.licenta.rapoarteimobiliare.entities.AreaEntity;
import com.licenta.rapoarteimobiliare.entities.UserEntity;

import java.util.Date;
import java.util.Set;

public class EvaluationReportDTO {
    private String name;
    private Date date;
    private UserEntity user;
    private AreaEntity area;
    private String type_property;
    private String tipImobil;
    private Integer numarCamere;
    private Integer suprafataMinima;
    private Integer anConstructie;
    private Set<String> facilitati;

    public EvaluationReportDTO(String name, Date date, UserEntity user, AreaEntity area, String type_property, String tipImobil, Integer numarCamere, Integer suprafataMinima, Integer anConstructie, Set<String> facilitati) {
        this.name = name;
        this.date = date;
        this.user = user;
        this.area = area;
        this.type_property = type_property;
        this.tipImobil = tipImobil;
        this.numarCamere = numarCamere;
        this.suprafataMinima = suprafataMinima;
        this.anConstructie = anConstructie;
        this.facilitati = facilitati;
    }

    public EvaluationReportDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public AreaEntity getArea() {
        return area;
    }

    public void setArea(AreaEntity area) {
        this.area = area;
    }

    public String getType_property() {
        return type_property;
    }

    public void setType_property(String type_property) {
        this.type_property = type_property;
    }

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
