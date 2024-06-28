package com.licenta.rapoarteimobiliare.DTO;



import java.util.Date;
import java.util.List;
import java.util.Set;

public class EvaluationReportDTO {
    private String name;
    private Date date;
    private UserDTO user;
    private AreaDTO area;
    private String type_property;
    private String tipImobil;
    private Integer numarCamere;
    private Integer suprafataMinima;
    private Integer anConstructie;
    private Set<String> facilitati;
    private double pretAjustatPerMp;
    private double pretMinimPerMp;
    private double pretMaximPerMp;

    private double pretMinimProprietate;
    private double pretMediuProprietate;
    private double pretMaximProprietate;
    private List<PropertyListing> propertyListings;

    public EvaluationReportDTO(String name, Date date, UserDTO user, AreaDTO area, String type_property, String tipImobil, Integer numarCamere, Integer suprafataMinima, Integer anConstructie, Set<String> facilitati) {
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

    public double getPretAjustatPerMp() {
        return pretAjustatPerMp;
    }

    public void setPretAjustatPerMp(double pretAjustatPerMp) {
        this.pretAjustatPerMp = pretAjustatPerMp;
    }

    public List<PropertyListing> getPropertyListings() {
        return propertyListings;
    }

    public void setPropertyListings(List<PropertyListing> propertyListings) {
        this.propertyListings = propertyListings;
    }

    public double getPretMinimPerMp() {
        return pretMinimPerMp;
    }

    public void setPretMinimPerMp(double pretMinimPerMp) {
        this.pretMinimPerMp = pretMinimPerMp;
    }

    public double getPretMaximPerMp() {
        return pretMaximPerMp;
    }

    public void setPretMaximPerMp(double pretMaximPerMp) {
        this.pretMaximPerMp = pretMaximPerMp;
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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public AreaDTO getArea() {
        return area;
    }

    public void setArea(AreaDTO area) {
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

    public double getPretMinimProprietate() {
        return pretMinimProprietate;
    }

    public void setPretMinimProprietate(double pretMinimProprietate) {
        this.pretMinimProprietate = pretMinimProprietate;
    }

    public double getPretMediuProprietate() {
        return pretMediuProprietate;
    }

    public void setPretMediuProprietate(double pretMediuProprietate) {
        this.pretMediuProprietate = pretMediuProprietate;
    }

    public double getPretMaximProprietate() {
        return pretMaximProprietate;
    }

    public void setPretMaximProprietate(double pretMaximProprietate) {
        this.pretMaximProprietate = pretMaximProprietate;
    }
}

