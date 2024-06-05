package com.licenta.rapoarteimobiliare.repositories;

import com.licenta.rapoarteimobiliare.entities.EvaluationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRepository extends JpaRepository<EvaluationEntity, Integer> {
}
