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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.biblioteca.foro.dto.TemaRequest;
import cl.duoc.biblioteca.foro.dto.TemaResponse;
import cl.duoc.biblioteca.foro.service.TemaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/api/temas")
public class TemaController {

    private final TemaService temaService;

    public TemaController(TemaService temaService) {
        this.temaService = temaService;
    }

    @GetMapping
    public List<TemaResponse> listar(@RequestParam(required = false) String texto,
            @RequestParam(required = false) Long categoriaId) {
        return temaService.listar(texto, categoriaId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TemaResponse> buscarPorId(@PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        Optional<TemaResponse> tema = temaService.buscarPorId(id);
        return tema.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TemaResponse> crear(@Valid @RequestBody TemaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(temaService.crear(request));
    }

    @PutMapping("/{id}")
        public ResponseEntity<TemaResponse> actualizar(@PathVariable @Positive(message = "El id debe ser positivo") Long id,
            @Valid @RequestBody TemaRequest request) {
        Optional<TemaResponse> tema = temaService.actualizar(id, request);
        return tema.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        if (!temaService.eliminar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}