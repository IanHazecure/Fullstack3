package cl.duoc.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.biblioteca.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {
}
