package com.microservices_usuarios.Service;

import com.microservices_usuarios.Enum.ROLES;
import com.microservices_usuarios.Mapper.UsuarioMapper;
import com.microservices_usuarios.Model.Usuario;
import com.microservices_usuarios.Repository.UsuarioRepository;
import com.microservices_usuarios.VO.UsuarioVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    public ResponseEntity<UsuarioVO> createUsuario(UsuarioVO usuarioVO) {
        Usuario usuario = usuarioMapper.INSTANCE.usuarioVOToUsuario(usuarioVO);

        if (usuario.getRoles() == null || usuario.getRoles().isEmpty()) {
            usuario.setRoles(new HashSet<>());
            usuario.getRoles().add(ROLES.USER);
        }
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.INSTANCE.usuarioToUsuarioVO(usuarioSalvo));
    }

    public ResponseEntity<UsuarioVO> updateUsuario(UsuarioVO usuarioVO){
        Optional<Usuario> usuario = usuarioRepository.findById(usuarioVO.getId());
        if(usuario.isPresent()){
            Usuario usuario1 = usuario.get();

            if(usuarioVO.getCep() != null){
                usuario1.setCep(usuarioVO.getCep());
            }
            if(usuarioVO.getEmail() != null){
                usuario1.setEmail(usuarioVO.getEmail());
            }
            if(usuarioVO.getLogin() != null){
                usuario1.setLogin(usuarioVO.getLogin());
            }
            if(usuarioVO.getCpf() != null){
                usuario1.setCpf(usuarioVO.getCpf());
            }
            if(usuarioVO.getPassword() != null){
                usuario1.setPassword(usuarioVO.getPassword());
            }
            if(usuarioVO.getUserName() != null){
                usuario1.setUserName(usuarioVO.getUserName());
            }
            if(usuarioVO.getRoles() != null){
                usuario1.setRoles(usuarioVO.getRoles());
            }
            Usuario usuarioSalvo = usuarioRepository.save(usuario1);
            return ResponseEntity.status(HttpStatus.OK).body(usuarioMapper.INSTANCE.usuarioToUsuarioVO(usuarioSalvo));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    public ResponseEntity<?> deleteUsuario(Long id){
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(usuario.isPresent()){
            usuarioRepository.delete(usuario.get());
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    public ResponseEntity<Page<UsuarioVO>> findAllUsuarios(Pageable pageable) {
        Page<Usuario> userPage = usuarioRepository.findAll(pageable);
        Page<UsuarioVO> usuariosVO = userPage.map(user -> usuarioMapper.usuarioToUsuarioVO(user));
        return ResponseEntity.status(HttpStatus.OK).body(usuariosVO);
    }

    public ResponseEntity<UsuarioVO> getUsuario(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(usuarioMapper.INSTANCE.usuarioToUsuarioVO(usuario.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
