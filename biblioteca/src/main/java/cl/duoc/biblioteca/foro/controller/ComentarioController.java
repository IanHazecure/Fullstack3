package cl.duoc.biblioteca.foro.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.biblioteca.foro.dto.ComentarioRequest;
import cl.duoc.biblioteca.foro.dto.ComentarioResponse;
import cl.duoc.biblioteca.foro.service.ComentarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/api")
public class ComentarioController {

    private final ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    @GetMapping("/temas/{temaId}/comentarios")
        public List<ComentarioResponse> listarPorTema(
            @PathVariable @Positive(message = "El id del tema debe ser positivo") Long temaId) {
        return comentarioService.listarPorTema(temaId);
    }

    @PostMapping("/temas/{temaId}/comentarios")
        public ResponseEntity<ComentarioResponse> crear(
            @PathVariable @Positive(message = "El id del tema debe ser positivo") Long temaId,
            @Valid @RequestBody ComentarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(comentarioService.crear(temaId, request));
    }

    @PutMapping("/comentarios/{id}")
        public ResponseEntity<ComentarioResponse> actualizar(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id,
            @Valid @RequestBody ComentarioRequest request) {
        Optional<ComentarioResponse> comentario = comentarioService.actualizar(id, request);
        return comentario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/comentarios/{id}")
        public ResponseEntity<Void> eliminar(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        if (!comentarioService.eliminar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}