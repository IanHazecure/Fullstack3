package cl.duoc.editorial.service;

import cl.duoc.editorial.factory.LibroFactory;
import cl.duoc.editorial.model.Libro;
import cl.duoc.editorial.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    private final LibroRepository repo;

    public LibroService(LibroRepository repo) {
        this.repo = repo;
    }

    public List<Libro> findAll() {
        return repo.findAll();
    }

    public Optional<Libro> findById(Long id) {
        return repo.findById(id);
    }

    public Libro save(String titulo, String autor, Integer anio, String genero) {
        return repo.save(LibroFactory.crear(titulo, autor, anio, genero));
    }

    public Libro update(Long id, Libro data) {
        Libro libro = repo.findById(id).orElseThrow();
        libro.setTitulo(data.getTitulo());
        libro.setAutor(data.getAutor());
        libro.setAnioPublicacion(data.getAnioPublicacion());
        libro.setGenero(data.getGenero());
        return repo.save(libro);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}