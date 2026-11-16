package cl.duoc.libros.controller;

import cl.duoc.libros.model.Libro;
import cl.duoc.libros.service.LibroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {
    private final LibroService service;

    public LibroController(LibroService service) { this.service = service; }

    @GetMapping
    public List<Libro> all() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> byId(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Libro create(@RequestBody Libro libro) {
        return service.save(libro.getTitulo(), libro.getAutor(), libro.getAnioPublicacion(), libro.getGenero());
    }

    @PutMapping("/{id}")
    public Libro update(@PathVariable Long id, @RequestBody Libro libro) {
        return service.update(id, libro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
