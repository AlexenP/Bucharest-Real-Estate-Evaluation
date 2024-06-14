package com.licenta.rapoarteimobiliare.entities;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "authority_entity")
public class AuthorityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int  id;

    @Column(nullable = false, unique = true)
    private String authority;

    @ManyToMany(mappedBy = "authorities")
    private Set<UserEntity> users;

    // getters and setters

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }
}
