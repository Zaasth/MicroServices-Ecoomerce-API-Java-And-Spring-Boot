package com.microservices_usuarios.VO;

import com.microservices_usuarios.Enum.ROLES;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioVO {

    private long id;
    private String userName;
    private String login;
    private String email;
    private String password;
    private String cpf;
    private String cep;
    private Set<ROLES> roles;
}
