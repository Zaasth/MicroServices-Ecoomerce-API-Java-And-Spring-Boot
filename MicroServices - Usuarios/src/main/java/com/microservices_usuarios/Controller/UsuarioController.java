package com.microservices_usuarios.Controller;

import com.microservices_usuarios.Mapper.UsuarioMapper;
import com.microservices_usuarios.Model.Usuario;
import com.microservices_usuarios.Repository.UsuarioRepository;
import com.microservices_usuarios.Service.UsuarioService;
import com.microservices_usuarios.VO.UsuarioVO;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios/v1")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioMapper usuarioMapper;

    @GetMapping
    @Cacheable(value = "users", key = "#page + '-' + #limit")
    public ResponseEntity<Page<UsuarioVO>> findAllUsuarios(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", required = false) Integer limit
    ) {

        int pageSize = (limit == null || limit == 0) ? Integer.MAX_VALUE : limit;
        Pageable pageable = PageRequest.of(page, pageSize);

        return usuarioService.findAllUsuarios(pageable);
    }

    @GetMapping("/{id}")
    @Cacheable(value = "users", key = "#id")
    public ResponseEntity<UsuarioVO> getUsuarioById(@PathVariable Long id) {
        try {
            return usuarioService.getUsuario(id);
        }catch (Exception e){
            System.out.println(e.getCause());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/create")
    @CacheEvict(value = "users", allEntries = true)
    public ResponseEntity<UsuarioVO> createUsuario(@RequestBody UsuarioVO usuarioVO) {
        Optional<Usuario> usuarioBuscado = usuarioRepository.findById(usuarioVO.getId());

        if(usuarioBuscado.isPresent()){
            System.out.println("Usuário já existe no banco de dados;");
            return ResponseEntity.badRequest().build();
        }
        return usuarioService.createUsuario(usuarioVO);
    }

    @PatchMapping("/update")
    @CachePut(value = "users", key = "#usuarioVO.id")
    public ResponseEntity<UsuarioVO> updateUsuario(@RequestBody UsuarioVO usuarioVO){

        Optional<Usuario> usuarioBuscado = usuarioRepository.findById(usuarioVO.getId());
        if(usuarioBuscado.isPresent()){
            return usuarioService.updateUsuario(usuarioVO);
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id){

        Optional<Usuario> usuarioBuscado = usuarioRepository.findById(id);
        if(usuarioBuscado.isPresent()){
            return usuarioService.deleteUsuario(id);
        }
        return ResponseEntity.badRequest().build();
    }
}
