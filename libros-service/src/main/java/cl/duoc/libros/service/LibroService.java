package cl.duoc.libros.service;

import cl.duoc.libros.factory.LibroFactory;
import cl.duoc.libros.model.Libro;
import cl.duoc.libros.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {
    private final LibroRepository repo;

    public LibroService(LibroRepository repo) { this.repo = repo; }

    public List<Libro> findAll() { return repo.findAll(); }
    public Optional<Libro> findById(Long id) { return repo.findById(id); }
    public Libro save(String titulo, String autor, Integer anio, String genero) {
        return repo.save(LibroFactory.crear(titulo, autor, anio, genero));
    }
    public Libro update(Long id, Libro data) {
        Libro l = repo.findById(id).orElseThrow();
        l.setTitulo(data.getTitulo());
        l.setAutor(data.getAutor());
        l.setAnioPublicacion(data.getAnioPublicacion());
        l.setGenero(data.getGenero());
        return repo.save(l);
    }
    public void delete(Long id) { repo.deleteById(id); }
}
