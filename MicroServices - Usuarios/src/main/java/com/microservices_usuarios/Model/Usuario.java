package com.microservices_usuarios.Model;

import com.microservices_usuarios.Enum.ROLES;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.service.spi.InjectService;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userName;

    @Column(unique=true)
    private String login;

    @Column(unique=true)
    private String email;

    private String password;

    @Column(unique=true)
    private String cpf;

    private String cep;

    @ElementCollection(targetClass = ROLES.class)
    @CollectionTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "usuario_id"))
    @Enumerated(EnumType.STRING)
    private Set<ROLES> roles = new HashSet<>();

}
