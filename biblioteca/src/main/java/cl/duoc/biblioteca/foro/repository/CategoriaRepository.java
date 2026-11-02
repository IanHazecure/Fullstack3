package cl.duoc.biblioteca.foro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.biblioteca.foro.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    boolean existsByNombreIgnoreCase(String nombre);
}