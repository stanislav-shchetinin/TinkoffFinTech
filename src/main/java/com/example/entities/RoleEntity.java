package com.example.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@ToString
@Setter
@Getter
@Entity
@Table(name = "roles")
public class RoleEntity implements GrantedAuthority {

    @Id
    @Column(name = "role")
    private String roleName;

    @Override
    public String getAuthority() {
        return roleName;
    }
}
