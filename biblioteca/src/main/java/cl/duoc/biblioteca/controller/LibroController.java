package cl.duoc.biblioteca.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import cl.duoc.biblioteca.model.Libro;
import cl.duoc.biblioteca.service.LibroService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    public List<Libro> listar() {
        return libroService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> buscarPorId(@PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        Optional<Libro> libro = libroService.buscarPorId(id);
        return libro.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Libro> crear(@Valid @RequestBody Libro datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(libroService.crear(datos));
    }

    @PutMapping("/{id}")
        public ResponseEntity<Libro> actualizar(@PathVariable @Positive(message = "El id debe ser positivo") Long id,
            @Valid @RequestBody Libro datos) {
        Optional<Libro> libro = libroService.actualizar(id, datos);
        return libro.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        if (!libroService.eliminar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
