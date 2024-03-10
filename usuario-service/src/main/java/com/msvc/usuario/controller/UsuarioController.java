package com.msvc.usuario.controller;

import com.msvc.usuario.entity.Usuario;
import com.msvc.usuario.service.UsuarioService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    int cantidadReintentos = 1;
    @PostMapping
    public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuarioRequest){
        Usuario usuario = usuarioService.saveUsuario(usuarioRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @GetMapping("/{usuarioId}")
    // @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable String usuarioId){
        log.info("Cantidad reintentos : {}", cantidadReintentos);
        cantidadReintentos++;
        Usuario usuario = usuarioService.getUsuario(usuarioId);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping()
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        return ResponseEntity.ok(usuarios);
    }


    public ResponseEntity<Usuario> ratingHotelFallback(String usuarioId, Exception exception){
        log.info("El respaldo se ejecuta porque el service está inactivo: ", exception.getMessage());
        Usuario usuario = Usuario.builder()
                .email("root1@gmail.com")
                .nombre("root")
                .informacion("Este usuario se creará por defecto cuando un MS cae")
                .usuarioId("1234")
                .build();
        return new ResponseEntity<>(usuario,HttpStatus.OK);
    }
}
