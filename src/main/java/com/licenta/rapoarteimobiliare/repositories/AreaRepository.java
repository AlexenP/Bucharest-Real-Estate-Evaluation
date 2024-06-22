package com.licenta.rapoarteimobiliare.repositories;

import com.licenta.rapoarteimobiliare.entities.AreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepository extends JpaRepository<AreaEntity, Integer>{
    AreaEntity findByAreaName(String areaName);
    boolean existsByAreaName(String areaName);
}
