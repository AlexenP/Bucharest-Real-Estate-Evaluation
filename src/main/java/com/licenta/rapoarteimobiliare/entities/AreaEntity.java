package com.licenta.rapoarteimobiliare.entities;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "area_entity", uniqueConstraints = @UniqueConstraint(columnNames = "areaName"))
public class AreaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int areaId;
    @Column(unique = true, nullable = false)
    private String areaName;
    private float pricePerSquareMeter;
    private boolean hasWaterIssues;
    private int levelofArea;

    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL)
    private Set<EvaluationEntity> evaluations;

    // Getters and Setters
    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public float getPricePerSquareMeter() {
        return pricePerSquareMeter;
    }

    public void setPricePerSquareMeter(float pricePerSquareMeter) {
        this.pricePerSquareMeter = pricePerSquareMeter;
    }

    public boolean isHasWaterIssues() {
        return hasWaterIssues;
    }

    public void setHasWaterIssues(boolean hasWaterIssues) {
        this.hasWaterIssues = hasWaterIssues;
    }

    public int getLevelofArea() {
        return levelofArea;
    }

    public void setLevelofArea(int levelofArea) {
        this.levelofArea = levelofArea;
    }

    public Set<EvaluationEntity> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(Set<EvaluationEntity> evaluations) {
        this.evaluations = evaluations;
    }
}
