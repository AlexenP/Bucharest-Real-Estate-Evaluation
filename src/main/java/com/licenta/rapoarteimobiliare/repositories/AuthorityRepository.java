package com.licenta.rapoarteimobiliare.repositories;

import com.licenta.rapoarteimobiliare.entities.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Integer> {
    AuthorityEntity findByAuthority(String roleUser);

}
