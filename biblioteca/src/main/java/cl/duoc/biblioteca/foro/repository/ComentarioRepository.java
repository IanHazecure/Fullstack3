package cl.duoc.biblioteca.foro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import cl.duoc.biblioteca.foro.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    @EntityGraph(attributePaths = { "autor" })
    List<Comentario> findByTemaIdOrderByFechaCreacionAsc(Long temaId);
}