package cl.duoc.biblioteca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.duoc.biblioteca.model.Libro;
import cl.duoc.biblioteca.repository.LibroRepository;

@Service
public class LibroService {



    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public List<Libro> listar() {

        return libroRepository.findAll();
        
    }
///////////
    public Optional<Libro> buscarPorId(Long id) {
        return libroRepository.findById(id);
    }

    public Libro crear(Libro datos) {
        Libro nuevo = new Libro(
                null,
                datos.getTitulo(),
                datos.getAutor(),
                datos.getAnioPublicacion(),
                datos.getGenero());
        return libroRepository.save(nuevo);
    }
    /////////////////

    public Optional<Libro> actualizar(Long id, Libro datos) {
        Optional<Libro> encontrado = libroRepository.findById(id);
        if (encontrado.isEmpty()) {
            return Optional.empty();
        }
        Libro actual = encontrado.get();
        actual.setTitulo(datos.getTitulo());
        actual.setAutor(datos.getAutor());
        actual.setAnioPublicacion(datos.getAnioPublicacion());
        actual.setGenero(datos.getGenero());
        return Optional.of(libroRepository.save(actual));


    }



    public boolean eliminar(Long id) {
        if (!libroRepository.existsById(id)) {
            return false;
        }

        libroRepository.deleteById(id);
        return true;
    }


}
