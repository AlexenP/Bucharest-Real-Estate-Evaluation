package com.licenta.rapoarteimobiliare.entities;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class EvaluationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int evaluationId;

    private String name;

    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private AreaEntity area;

    private String type_property;

    private String tipImobil;

    private Integer numarCamere;

    private Integer suprafataMinima;

    private Integer anConstructie;

    @ElementCollection
    private Set<String> facilitati;

    // Getters and Setters

    public int getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(int evaluationId) {
        this.evaluationId = evaluationId;
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
