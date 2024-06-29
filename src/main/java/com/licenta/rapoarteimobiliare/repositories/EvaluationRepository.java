package com.licenta.rapoarteimobiliare.repositories;

import com.licenta.rapoarteimobiliare.entities.EvaluationEntity;
import com.licenta.rapoarteimobiliare.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<EvaluationEntity, Integer> {
    List<EvaluationEntity> findByUser(UserEntity user);

    List<EvaluationEntity> findByUserUsername(String username);
}
