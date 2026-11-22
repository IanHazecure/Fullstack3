package ${package}.controller;

import ${package}.model.Libro;
import ${package}.service.LibroService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
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

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroService service;

    public LibroController(LibroService service) {
        this.service = service;
    }

    @GetMapping
    public List<Libro> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> byId(@PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Libro create(@Valid @RequestBody Libro libro) {
        return service.save(libro.getTitulo(), libro.getAutor(), libro.getAnioPublicacion(), libro.getGenero());
    }

    @PutMapping("/{id}")
    public Libro update(@PathVariable @Positive(message = "El id debe ser positivo") Long id,
            @Valid @RequestBody Libro libro) {
        return service.update(id, libro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}