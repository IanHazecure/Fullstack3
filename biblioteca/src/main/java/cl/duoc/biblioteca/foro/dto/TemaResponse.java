package cl.duoc.biblioteca.foro.dto;

import java.time.LocalDateTime;

public record TemaResponse(Long id, String titulo, String contenido, LocalDateTime fechaCreacion,
        Long categoriaId, String categoria, Long autorId, String autor) {
}