package cl.duoc.biblioteca.foro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import cl.duoc.biblioteca.foro.model.Tema;

public interface TemaRepository extends JpaRepository<Tema, Long> {

    @Override
    @EntityGraph(attributePaths = { "categoria", "autor" })
    List<Tema> findAll();

    @EntityGraph(attributePaths = { "categoria", "autor" })
    List<Tema> findByCategoriaId(Long categoriaId);
}