package com.microservices_usuarios.Mapper;

import com.microservices_usuarios.Model.Usuario;
import com.microservices_usuarios.VO.UsuarioVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper extends Serializable {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    UsuarioVO usuarioToUsuarioVO(Usuario usuario);

    Usuario usuarioVOToUsuario(UsuarioVO usuarioVO);

    List<UsuarioVO> usuarioListToUsuarioVOList(List<Usuario> usuarioList);

    List<Usuario> usuarioVOListToUsuarioList(List<UsuarioVO> usuarioVOList);


}
